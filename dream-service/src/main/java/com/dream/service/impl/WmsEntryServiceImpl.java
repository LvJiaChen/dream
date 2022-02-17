package com.dream.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.common.entity.WmsEntry;
import com.dream.common.entity.WmsMaterial;
import com.dream.common.mapper.WmsEntryMapper;
import com.dream.common.vo.WmsEntryPageVo;
import com.dream.service.IWmsEntryService;
import com.dream.service.IWmsSerialNumberService;
import com.dream.service.utils.ListMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-25
 */
@Service
public class WmsEntryServiceImpl extends ServiceImpl<WmsEntryMapper, WmsEntry> implements IWmsEntryService {

    @Autowired
    private IWmsSerialNumberService iWmsSerialNumberService;
    @Autowired
    private WmsEntryMapper wmsEntryMapper;

    @Override
    public void saveEntry(Map param) {
        List<WmsEntry> entryList= ListMapUtils.toListBean((List<Map>) param.get("entryData"),WmsEntry.class);
        String code= iWmsSerialNumberService.generateSerialNumberByModelCode("wms_entry_code");
        entryList.forEach(a->{
            String batch= iWmsSerialNumberService.generateSerialNumberByModelCode("wms_entry_batch");
            a.setCode(code);
            a.setBatch(batch);
            a.setEntryDate(DateUtil.parse((String) param.get("entryDate")));
            a.setWarehouseCode((String) param.get("warehouseCode"));
            a.setStatus("入库");
            wmsEntryMapper.insert(a);
        });
    }

    @Override
    public IPage<WmsEntryPageVo> queryEntryListPage(Map param) {
        Page<WmsEntryPageVo> entryPageVoPage = new Page<>((Integer)param.get("pageIndex") , (Integer)param.get("pageSize") , false);
        entryPageVoPage.setSearchCount(true);
        IPage<WmsEntryPageVo> pageVos= wmsEntryMapper.queryEntryListPage(entryPageVoPage,(String) param.get("code"),(String) param.get("materialName"));
        return pageVos;
    }
}
