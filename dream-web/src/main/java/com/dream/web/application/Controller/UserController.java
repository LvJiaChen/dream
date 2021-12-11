package com.dream.web.application.Controller;


import com.dream.common.entity.User;
import com.dream.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lvxiaozuo
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/common/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping(value = "hello")
    public String hello(){
        User user = userService.selectUserNameBy(1);
        user.setAge(100);
        userService.updateById(user);
        List<User> userToString = userService.queryUser(1);
        return "userToString";
    }

}
