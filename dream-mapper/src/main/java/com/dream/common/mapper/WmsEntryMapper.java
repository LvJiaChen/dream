package com.dream.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.common.entity.WmsEntry;
import com.dream.common.vo.WmsEntryDetailVo;
import com.dream.common.vo.WmsEntryPageVo;
import org.apache.ibatis.annotations.Param;

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
public interface WmsEntryMapper extends BaseMapper<WmsEntry> {

    IPage<WmsEntryPageVo> queryEntryListPage(Page<WmsEntryPageVo> entryPageVoPage, @Param("code") String code,@Param("materialName") String materialName);

    List<WmsEntryDetailVo> queryEntryDetail(Map param);
}
