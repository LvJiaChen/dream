package com.dream.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.common.entity.WmsRequisition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dream.common.vo.WmsRequisitionDetailVo;
import com.dream.common.vo.WmsRequisitionPageVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-25
 */
public interface WmsRequisitionMapper extends BaseMapper<WmsRequisition> {

    IPage<WmsRequisitionPageVo> queryRequisitionListPage(Page<WmsRequisitionPageVo> entryPageVoPage, String code, String materialName);

    List<WmsRequisitionDetailVo> queryRequisitionDetail(Map param);
}
