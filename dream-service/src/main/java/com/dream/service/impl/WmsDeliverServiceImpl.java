package com.dream.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.common.entity.*;
import com.dream.common.mapper.WmsDeliverMapper;
import com.dream.common.mapper.WmsRequisitionMapper;
import com.dream.common.vo.WmsDeliverPageVo;
import com.dream.common.vo.WmsEntryPageVo;
import com.dream.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.service.utils.ListMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Autowired
    private IWmsRequisitionService requisitionService;
    @Autowired
    private IWmsDeliverBatchService deliverBatchService;

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
        stockQueryWrapper.in("material_no",materialNoList)
                .eq("warehouse_code",param.get("warehouseCode"))
                .gt("quantity",0)
                .orderByAsc("id");
        List<WmsStock> stocks=stockService.list(stockQueryWrapper);
        //更新的库存
        List<WmsStock> updateStocks=new ArrayList<>();
        //出库批次表
        List<WmsDeliverBatch> deliverBatchList=new ArrayList<>();
        //查询领料单数据
        QueryWrapper<WmsRequisition> requisitionQueryWrapper=new QueryWrapper<>();
        requisitionQueryWrapper.eq("code",param.get("referenceNo"));
        List<WmsRequisition> requisitionList=requisitionMapper.selectList(requisitionQueryWrapper);
        List<WmsRequisition> updateRequisitionList=new ArrayList<>();
        //出库单号
        String code= iWmsSerialNumberService.generateSerialNumberByModelCode("wms_deliver_code");

        deliverList.forEach(a->{
            Integer deliverItem=1;
            //出库金额
            BigDecimal moneyAll=BigDecimal.ZERO;
            BigDecimal quantityAll=BigDecimal.ZERO;
            //校验物料编码是否为空
            if (StrUtil.isBlank(a.getMaterialNo())){
                throw new RuntimeException("物料编码不能为空");
            }
            //入库数量不能为0校验
            if (a.getQuantity().compareTo(BigDecimal.ZERO)==0){
                throw new RuntimeException("物料："+a.getMaterialNo()+"出库数量不能等于0");
            }
            //分配库存
            for (WmsStock stock : stocks) {
                if (a.getMaterialNo().equals(stock.getMaterialNo())){
                    if (a.getQuantity().compareTo(BigDecimal.ZERO)>0){
                        WmsStock updateStock=new WmsStock();
                        BeanUtil.copyProperties(stock,updateStock);
                        if (stock.getQuantity().compareTo(a.getQuantity())>=0){
                            //如果库存数量大于出库数量
                            stock.setQuantity(stock.getQuantity().subtract(a.getQuantity()));
                            updateStock.setQuantity(a.getQuantity());
                            updateStock.setMoney(updateStock.getPrice().multiply(updateStock.getQuantity())
                                    .setScale(2, RoundingMode.HALF_UP));
                            a.setQuantity(BigDecimal.ZERO);
                        }else {
                            //如果库存数量小于称出库数量
                            a.setQuantity(a.getQuantity().subtract(stock.getQuantity()));
                            stock.setQuantity(BigDecimal.ZERO);
                        }
                        updateStocks.add(updateStock);
                        WmsDeliverBatch deliverBatch=new WmsDeliverBatch();
                        BeanUtil.copyProperties(updateStock,deliverBatch);
                        deliverBatch.setCode(code);
                        deliverBatch.setDeliverItem(deliverItem+"");
                        moneyAll=moneyAll.add(deliverBatch.getMoney());
                        quantityAll=quantityAll.add(deliverBatch.getQuantity());
                        deliverBatchList.add(deliverBatch);
                    }
                }
            }
            if (a.getQuantity().compareTo(BigDecimal.ZERO)>0){
                throw new RuntimeException("物料："+a.getMaterialNo()+"库存不足");
            }
            //修改领料单状态
            for (WmsRequisition r : requisitionList) {
                if (a.getMaterialNo().equals(r.getMaterialNo())){
                    r.setDeliverQuantity(r.getDeliverQuantity().add(quantityAll));
                    r.setStatus("部分出库");
                    if (r.getDeliverQuantity().equals(r.getQuantity())){
                        r.setStatus("全部出库");
                    }
                    updateRequisitionList.add(r);
                    break;
                }
            }
            a.setCode(code);
            a.setDeliverItem(deliverItem+"");
            a.setDeliverDate(DateUtil.parse((String) param.get("deliverDate")));
            a.setWarehouseCode((String) param.get("warehouseCode"));
            a.setReferenceNo((String) param.get("referenceNo"));
            a.setStatus("出库");
            a.setMoney(moneyAll);
            a.setQuantity(quantityAll);
            if(a.getQuantity().compareTo(BigDecimal.ZERO)!=0){
                a.setPrice(a.getMoney().divide(a.getQuantity(),2,RoundingMode.HALF_UP));
            }
            deliverItem++;
        });
        this.saveBatch(deliverList);
        requisitionService.updateBatchById(updateRequisitionList);
        deliverBatchService.saveBatch(deliverBatchList);
        //修改库存
        stockService.changeStock(updateStocks,"deliver");
    }
}
