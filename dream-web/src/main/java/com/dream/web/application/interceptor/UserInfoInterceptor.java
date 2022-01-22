package com.dream.web.application.interceptor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.common.entity.WmsUser;
import com.dream.common.vo.UserInfo;
import com.dream.service.IWmsUserService;
import com.dream.web.application.utils.Result;
import com.dream.web.application.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lvxiaozuo
 * @date 2022/1/22 17:08
 */
@Slf4j
public class UserInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private IWmsUserService iWmsUserService;
    /**
     * 请求执行前执行的，将用户信息放入ThreadLocal
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = TokenUtil.getRequestToken(request);
        //1. 根据token，查询用户信息
        QueryWrapper<WmsUser> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("token",token);
        WmsUser user = iWmsUserService.getOne(userQueryWrapper);
        UserInfo.setUser(user);
        return true;
    }

    /**
     * 接口访问结束后，从ThreadLocal中删除用户信息
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfo.removeUser();
    }

    //返回错误信息
    private static void setReturn(HttpServletResponse response, int status, String msg) throws IOException {
        //UTF-8编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Result build = Result.build(status, msg);
        String json = JSON.toJSONString(build);
        response.getWriter().print(json);
    }
}
