package com.dream.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.common.entity.WmsEntry;
import com.dream.common.entity.WmsStock;
import com.dream.common.mapper.WmsEntryMapper;
import com.dream.common.vo.WmsEntryDetailVo;
import com.dream.common.vo.WmsEntryPageVo;
import com.dream.service.IWmsEntryService;
import com.dream.service.IWmsSerialNumberService;
import com.dream.service.IWmsStockService;
import com.dream.service.utils.ListMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    private IWmsStockService wmsStockService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveEntry(Map param){
        List<WmsEntry> entryList= ListMapUtils.toListBean((List<Map>) param.get("entryData"),WmsEntry.class);
        String code= iWmsSerialNumberService.generateSerialNumberByModelCode("wms_entry_code");
        List<WmsStock> stocks=new ArrayList<>();
        entryList.forEach(a->{
            //校验物料编码是否为空
            if (StrUtil.isBlank(a.getMaterialNo())){
                throw new RuntimeException("物料编码不能为空");
            }
            //入库数量不能为0校验
            if (a.getQuantity().compareTo(BigDecimal.ZERO)==0){
                throw new RuntimeException("物料："+a.getMaterialNo()+"入库数量不能等于0");
            }
            String batch= iWmsSerialNumberService.generateSerialNumberByModelCode("wms_entry_batch");
            a.setCode(code);
            a.setBatch(batch);
            a.setEntryDate(DateUtil.parse((String) param.get("entryDate")));
            a.setWarehouseCode((String) param.get("warehouseCode"));
            a.setStatus("入库");
            WmsStock stock=new WmsStock();
            BeanUtil.copyProperties(a,stock);
            stocks.add(stock);
        });
        this.saveBatch(entryList);
        //修改库存
        wmsStockService.changeStock(stocks,"entry");
    }

    @Override
    public IPage<WmsEntryPageVo> queryEntryListPage(Map param) {
        Page<WmsEntryPageVo> entryPageVoPage = new Page<>((Integer)param.get("pageIndex") , (Integer)param.get("pageSize") , false);
        entryPageVoPage.setSearchCount(true);
        IPage<WmsEntryPageVo> pageVos= wmsEntryMapper.queryEntryListPage(entryPageVoPage,(String) param.get("code"),(String) param.get("materialName"));
        return pageVos;
    }

    @Override
    public List<WmsEntryDetailVo> queryEntryDetail(Map param) {
        List<WmsEntryDetailVo> entryDetailVos=wmsEntryMapper.queryEntryDetail(param);
        return entryDetailVos;
    }
}
