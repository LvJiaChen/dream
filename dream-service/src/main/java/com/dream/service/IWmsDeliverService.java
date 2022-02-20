package com.dream.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.entity.WmsDeliver;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.common.vo.WmsDeliverDetailVo;
import com.dream.common.vo.WmsDeliverPageVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-25
 */
public interface IWmsDeliverService extends IService<WmsDeliver> {

    IPage<WmsDeliverPageVo> queryDeliverListPage(Map param);

    void saveDeliver(Map param);

    List<WmsDeliverDetailVo> queryDeliverDetail(Map param);
}
