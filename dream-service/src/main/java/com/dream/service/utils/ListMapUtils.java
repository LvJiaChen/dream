package com.dream.service.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lvxiaozuo
 * @date 2022/2/17 9:53
 */
public class ListMapUtils {

    public static  <T> List<T> toListBean(List<Map> mapList,Class<T> clazz){
        List<T> list=new ArrayList<>();
        mapList.forEach(a->{
            T t= BeanUtil.toBean(a,clazz, CopyOptions.create());
            list.add(t);
        });
        return list;
    }
}
