package com.dream.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.common.entity.User;
import com.dream.common.mapper.UserMapper;
import com.dream.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2021-11-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    public User selectUserNameBy(Integer id) {
        User user= userMapper.selectById(id);
        return user;
    }

    public List<User> queryUser(int i) {
        List<User> userList= userMapper.queryUser(1);
        return userList;
    }
}
