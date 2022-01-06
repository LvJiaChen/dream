package com.dream.web.application.Controller;


import com.dream.common.entity.WmsUser;
import com.dream.service.IWmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-06
 */
@Controller
@RequestMapping("/wms-user")
public class WmsUserController {
    @Autowired
    private IWmsUserService userService;

    @GetMapping(value = "hello")
    public String hello(){
        WmsUser wmsUser=userService.getById(1);

        return "userToString";
    }
}
