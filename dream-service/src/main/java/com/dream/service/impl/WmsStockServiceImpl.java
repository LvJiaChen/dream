package com.dream.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.common.entity.WmsStock;
import com.dream.common.mapper.WmsStockMapper;
import com.dream.service.IWmsStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class WmsStockServiceImpl extends ServiceImpl<WmsStockMapper, WmsStock> implements IWmsStockService {
    @Autowired
    private WmsStockMapper stockMapper;

    @Override
    public void changeStock(List<WmsStock> stockList,String type) {
        if ("entry".equals(type)){
            //入库
            this.saveBatch(stockList);
        }else {
            Map<String, BigDecimal> batchQuantity= stockList.stream().collect(Collectors.toMap(WmsStock::getBatch,WmsStock::getQuantity));
            //出库
            List<String> batchList=  stockList.stream().map(WmsStock::getBatch).collect(Collectors.toList());
            QueryWrapper<WmsStock> queryWrapper=new QueryWrapper<>();
            queryWrapper.in("batch",batchList);
            List<WmsStock> stocks=stockMapper.selectList(queryWrapper);
            for (WmsStock s:stocks){
                s.setQuantity(s.getQuantity().subtract(batchQuantity.get(s.getBatch())));
                s.setMoney(s.getPrice().multiply(s.getQuantity()).setScale(2, RoundingMode.HALF_UP));
            }
            this.updateBatchById(stocks);
        }
    }
}
