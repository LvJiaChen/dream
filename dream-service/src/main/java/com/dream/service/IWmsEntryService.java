package com.dream.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.entity.WmsEntry;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.common.vo.WmsEntryPageVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-25
 */
public interface IWmsEntryService extends IService<WmsEntry> {

    void saveEntry(Map param);

    IPage<WmsEntryPageVo> queryEntryListPage(Map param);
}
