package com.dream.service;

import com.dream.common.entity.WmsStock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-25
 */
public interface IWmsStockService extends IService<WmsStock> {

    void changeStock(List<WmsStock> stock,String type);

}
