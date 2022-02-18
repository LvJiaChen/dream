package com.dream.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.entity.WmsRequisition;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.common.vo.WmsRequisitionDetailVo;
import com.dream.common.vo.WmsRequisitionPageVo;

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
public interface IWmsRequisitionService extends IService<WmsRequisition> {

    IPage<WmsRequisitionPageVo> queryRequisitionListPage(Map param);

    void saveRequisition(Map param);

    List<WmsRequisitionDetailVo> queryRequisitionDetail(Map param);
}
