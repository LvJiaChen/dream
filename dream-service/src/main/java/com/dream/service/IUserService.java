package com.dream.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.common.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2021-11-28
 */
public interface IUserService extends IService<User> {

    String selectUserNameBy(Integer id);
}
