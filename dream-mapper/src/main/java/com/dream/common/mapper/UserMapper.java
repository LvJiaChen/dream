package com.dream.common.mapper;

import com.dream.common.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lvxiaozuo
 * @since 2021-11-28
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> queryUser(int i);
}
