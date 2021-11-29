package com.dream.web.application.Controller;


import com.dream.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lvxiaozuo
 * @since 2021-11-28
 */
@Controller
@RequestMapping("/common/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        String userToString = userService.selectUserNameBy(1);
        System.out.println(userToString);
        return userToString;
    }

}
