package com.dream.service;

import com.dream.common.entity.WmsEntry;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
