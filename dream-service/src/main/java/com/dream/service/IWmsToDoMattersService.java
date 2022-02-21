package com.dream.service;

import com.dream.common.entity.WmsToDoMatters;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-02-21
 */
public interface IWmsToDoMattersService extends IService<WmsToDoMatters> {

    List<WmsToDoMatters> queryToDoMattersList(Map param);

    void queryToDoMattersStatus(Map param);

    void saveMatterPost(Map param);

    Map queryEntryDeliverStockToDate(Map param);

    Map queryEntryDeliverSchart(Map param);
}
