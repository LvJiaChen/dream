package com.dream.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.common.entity.WmsFoodCalorie;
import com.dream.common.mapper.WmsFoodCalorieMapper;
import com.dream.service.IWmsFoodCalorieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-02-10
 */
@Service
public class WmsFoodCalorieServiceImpl extends ServiceImpl<WmsFoodCalorieMapper, WmsFoodCalorie> implements IWmsFoodCalorieService {

    @Autowired
    private WmsFoodCalorieMapper wmsFoodCalorieMapper;


    @Override
    public IPage<WmsFoodCalorie> queryFoodCalorieList(Map param) {
        QueryWrapper<WmsFoodCalorie> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlank((String) param.get("foodName")))
            queryWrapper.like("food_name", param.get("foodName"));
        Page<WmsFoodCalorie> foodCaloriePage = new Page<>((Integer) param.get("pageIndex"), (Integer) param.get("pageSize"), false);
        foodCaloriePage.setSearchCount(true);
        IPage<WmsFoodCalorie> foodCalorieIPage = wmsFoodCalorieMapper.selectPage(foodCaloriePage, queryWrapper);
        return foodCalorieIPage;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void deleteFoodCalorie(Map param) {
        wmsFoodCalorieMapper.deleteById((Integer)param.get("id"));
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveFoodCalorie(Map param) {
        WmsFoodCalorie wmsFoodCalorie = BeanUtil.copyProperties(param, WmsFoodCalorie.class);
        if (wmsFoodCalorie.getId()==null){
            //添加
            wmsFoodCalorieMapper.insert(wmsFoodCalorie);
        }else {
            //编辑
            wmsFoodCalorieMapper.updateById(wmsFoodCalorie);
        }

    }
}
