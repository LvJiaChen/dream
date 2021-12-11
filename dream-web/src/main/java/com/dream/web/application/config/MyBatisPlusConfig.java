package com.dream.web.application.config;

import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lvxiaozuo
 * @date 2021/11/29 21:31
 */
@Configuration(proxyBeanMethods = false)
public class MyBatisPlusConfig {
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInnerInterceptor();
    }
}
