package com.dream.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.common.entity.WmsEntry;
import com.dream.common.entity.WmsMaterial;
import com.dream.common.mapper.WmsEntryMapper;
import com.dream.common.mapper.WmsMaterialMapper;
import com.dream.service.IWmsMaterialService;
import com.dream.service.IWmsSerialNumberService;
import com.dream.service.redis.CacheListCallback;
import com.dream.service.redis.CacheObjectCallback;
import com.dream.service.redis.CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-16
 */
@Service
public class WmsMaterialServiceImpl extends ServiceImpl<WmsMaterialMapper, WmsMaterial> implements IWmsMaterialService {
    @Autowired
    private WmsMaterialMapper materialMapper;
    @Autowired
    private IWmsSerialNumberService iWmsSerialNumberService;
    @Autowired
    private CacheTemplate cacheTemplate;
    @Autowired
    private WmsEntryMapper wmsEntryMapper;

    @Override
    public IPage<WmsMaterial> queryMaterialListPage(Map param) {
        QueryWrapper<WmsMaterial> queryWrapper=new QueryWrapper<>();
        if (!StrUtil.isBlank((String)param.get("materialNo")))
            queryWrapper.like("material_no",param.get("materialNo"));
        if (!StrUtil.isBlank((String)param.get("materialName")))
            queryWrapper.like("material_name",param.get("materialName"));
        Page<WmsMaterial> materialPage = new Page<>((Integer)param.get("pageIndex") , (Integer)param.get("pageSize") , false);
        materialPage.setSearchCount(true);
        IPage<WmsMaterial> materialIPage =  materialMapper.selectPage(materialPage,queryWrapper);
        return materialIPage;
    }

    @Override
    public void deleteMaterial(Map param) {
        WmsMaterial material= materialMapper.selectById((Integer)param.get("id"));
        QueryWrapper<WmsEntry> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("material_no",material.getMaterialNo());
        List<WmsEntry> entryList=wmsEntryMapper.selectList(queryWrapper);
        if (CollUtil.isNotEmpty(entryList)){
            throw new RuntimeException("该物料已入库不能删除");
        }
        materialMapper.deleteById((Integer)param.get("id"));
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveMaterial(Map param) {
        WmsMaterial material=BeanUtil.toBean(param,WmsMaterial.class, CopyOptions.create());
        if (material.getId()==null){
            String materialNo= iWmsSerialNumberService.generateSerialNumberByModelCode("wms_material");
            material.setMaterialNo(materialNo);
            //添加
            materialMapper.insert(material);
        }else {
            //编辑
            materialMapper.updateById(material);
        }
    }

    @Override
    public List<WmsMaterial> selectMaterial(Map param) {
        List<WmsMaterial> materialList= cacheTemplate.listCacheWrapper("测试", WmsMaterial.class, new CacheListCallback() {
            @Override
            public List<WmsMaterial> getLatestValues() {
                QueryWrapper<WmsMaterial> queryWrapper=new QueryWrapper<>();
                queryWrapper.like("material_name","测试");
                List<WmsMaterial> materialList=materialMapper.selectList(queryWrapper);
                return materialList;
            }
        });

        WmsMaterial material= cacheTemplate.objectCacheWrapper("M000007-20220124", WmsMaterial.class, new CacheObjectCallback() {
            @Override
            public WmsMaterial getLatestValue() {
                QueryWrapper<WmsMaterial> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("material_no","M000007-20220124");
                WmsMaterial material=materialMapper.selectOne(queryWrapper);
                return material;
            }
        });
        return Arrays.asList(material);
    }

    @Override
    public List<WmsMaterial> queryMaterialList(Map param) {
        QueryWrapper<WmsMaterial> queryWrapper=new QueryWrapper<>();
        if (!StrUtil.isBlank((String)param.get("value")))
            queryWrapper.like("material_name",param.get("value"))
                    .or().like("brand",param.get("value"))
                    .or().like("space",param.get("value"));
        List<WmsMaterial> wmsMaterialList= materialMapper.selectList(queryWrapper);
        return wmsMaterialList;
    }
}
