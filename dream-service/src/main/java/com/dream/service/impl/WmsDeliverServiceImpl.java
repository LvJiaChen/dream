package com.dream.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.common.entity.WmsDeliver;
import com.dream.common.entity.WmsEntry;
import com.dream.common.entity.WmsRequisition;
import com.dream.common.entity.WmsStock;
import com.dream.common.mapper.WmsDeliverMapper;
import com.dream.common.mapper.WmsRequisitionMapper;
import com.dream.common.vo.WmsDeliverPageVo;
import com.dream.common.vo.WmsEntryPageVo;
import com.dream.service.IWmsDeliverService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-25
 */
@Service
public class WmsDeliverServiceImpl extends ServiceImpl<WmsDeliverMapper, WmsDeliver> implements IWmsDeliverService {
    @Autowired
    private WmsDeliverMapper wmsDeliverMapper;
    @Autowired
    private WmsRequisitionMapper requisitionMapper;
    @Autowired
    private IWmsSerialNumberService iWmsSerialNumberService;
    @Autowired
    private IWmsStockService stockService;
    @Override
    public IPage<WmsDeliverPageVo> queryDeliverListPage(Map param) {
        Page<WmsDeliverPageVo> deliverPageVoPage = new Page<>((Integer)param.get("pageIndex") , (Integer)param.get("pageSize") , false);
        deliverPageVoPage.setSearchCount(true);
        IPage<WmsDeliverPageVo> pageVos= wmsDeliverMapper.queryDeliverListPage(deliverPageVoPage,(String) param.get("code")
                ,(String) param.get("materialName"),(String)param.get("referenceNo"));
        return pageVos;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveDeliver(Map param) {
        List<WmsDeliver> deliverList= ListMapUtils.toListBean((List<Map>) param.get("deliverData"),WmsDeliver.class);
        //出库的物料
        List<String> materialNoList=deliverList.stream().map(WmsDeliver::getMaterialNo).collect(Collectors.toList());
        //查询库存(默认先入先出)
        QueryWrapper<WmsStock> stockQueryWrapper=new QueryWrapper<>();
        stockQueryWrapper.in("materialNo",materialNoList)
                .eq("warehouse_code",param.get("warehouseCode"))
                .gt("quantity",0)
                .orderByAsc("id");
        List<WmsStock> stocks=stockService.list(stockQueryWrapper);
        //更新的库存
        List<WmsStock> updateStocks=new ArrayList<>();
        //查询领料单数据
        QueryWrapper<WmsRequisition> requisitionQueryWrapper=new QueryWrapper<>();
        requisitionQueryWrapper.eq("code",param.get("referenceNo"));
        List<WmsRequisition> requisitionList=requisitionMapper.selectList(requisitionQueryWrapper);
        List<WmsRequisition> updateRequisitionList=new ArrayList<>();
        //出库单号
        String code= iWmsSerialNumberService.generateSerialNumberByModelCode("wms_deliver_code");

        deliverList.forEach(a->{
            //分配库存
            for (WmsStock stock : stocks) {
                if (a.getMaterialNo().equals(stock.getMaterialNo())){
                    if (stock.getQuantity().compareTo(a.getQuantity())>0){
                        //如果库存数量大于出库数量
                        stock.setQuantity(stock.getQuantity().subtract(a.getQuantity()));
                        WmsStock updateStock=new WmsStock();
                        BeanUtil.copyProperties(stock,updateStock);
                        updateStock.setQuantity(a.getQuantity());
                        updateStocks.add(updateStock);
                    }
                }
            }
            //修改领料单状态
            for (WmsRequisition r : requisitionList) {
                if (a.getMaterialNo().equals(r.getMaterialNo())){
                    r.setDeliverQuantity(r.getDeliverQuantity().add(a.getQuantity()));
                    r.setStatus("部分出库");
                    if (r.getDeliverQuantity().equals(r.getQuantity())){
                        r.setStatus("全部出库");
                    }
                    updateRequisitionList.add(r);
                    break;
                }
            }

            //校验物料编码是否为空
            if (StrUtil.isBlank(a.getMaterialNo())){
                throw new RuntimeException("物料编码不能为空");
            }
            //入库数量不能为0校验
            if (a.getQuantity().compareTo(BigDecimal.ZERO)==0){
                throw new RuntimeException("物料："+a.getMaterialNo()+"出库数量不能等于0");
            }
            a.setCode(code);
            a.setDeliverDate(DateUtil.parse((String) param.get("deliverDate")));
            a.setWarehouseCode((String) param.get("warehouseCode"));
            a.setReferenceNo((String) param.get("referenceNo"));
            a.setStatus("出库");

            WmsStock stock=new WmsStock();
            BeanUtil.copyProperties(a,stock);
            stocks.add(stock);
        });
        this.saveBatch(deliverList);
        //修改库存
        stockService.changeStock(stocks,"deliver");
    }
}
