package com.dream.service.impl;

import com.dream.common.entity.WmsStock;
import com.dream.common.mapper.WmsStockMapper;
import com.dream.service.IWmsStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void changeStock(List<WmsStock> stock,String type) {
        if ("entry".equals(type)){
            //入库
            this.saveBatch(stock);
        }else {
            //出库
        }
    }
}
