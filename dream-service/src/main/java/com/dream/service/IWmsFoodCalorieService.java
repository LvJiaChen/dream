package com.dream.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.entity.WmsFoodCalorie;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.common.entity.WmsMaterial;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-02-10
 */
public interface IWmsFoodCalorieService extends IService<WmsFoodCalorie> {

    IPage<WmsFoodCalorie> queryFoodCalorieList(Map param);

    void deleteFoodCalorie(Map param);

    void saveFoodCalorie(Map param);

}
