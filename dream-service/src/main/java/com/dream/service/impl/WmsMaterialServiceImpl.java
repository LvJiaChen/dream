package com.dream.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.common.entity.WmsMaterial;
import com.dream.common.mapper.WmsMaterialMapper;
import com.dream.service.IWmsMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public IPage<WmsMaterial> queryMaterialList(Map param) {
        QueryWrapper<WmsMaterial> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("material_no",param.get("materialNo"));
        queryWrapper.like("material_name",param.get("materialName"));
        Page<WmsMaterial> materialPage = new Page<>((Integer)param.get("pageIndex") , (Integer)param.get("pageSize") , false);
        materialPage.setSearchCount(true);
        IPage<WmsMaterial> materialIPage =  materialMapper.selectPage(materialPage,queryWrapper);
        return materialIPage;
    }

    @Override
    public void deleteMaterial(Map param) {
        materialMapper.deleteById((Integer)param.get("id"));
    }
}
