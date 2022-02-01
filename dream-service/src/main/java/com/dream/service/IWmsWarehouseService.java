package com.dream.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.entity.WmsWarehouse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.common.vo.wms.WmsFoodCalorie;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-24
 */
public interface IWmsWarehouseService extends IService<WmsWarehouse> {

    IPage<WmsWarehouse> queryWarehouseList(Map param) throws Exception;

    void saveWarehouse(Map param);

    void deleteWarehouse(Map param);

    List<WmsFoodCalorie> queryFoodCalorie(Map map);
}
