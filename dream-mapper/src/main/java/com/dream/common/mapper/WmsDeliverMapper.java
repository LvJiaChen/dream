package com.dream.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.common.entity.WmsDeliver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dream.common.vo.WmsDeliverPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-25
 */
public interface WmsDeliverMapper extends BaseMapper<WmsDeliver> {

    IPage<WmsDeliverPageVo> queryDeliverListPage(Page<WmsDeliverPageVo> deliverPageVoPage, @Param("code") String code,
                                                 @Param("materialName") String materialName,@Param("referenceNo") String referenceNo);
}
