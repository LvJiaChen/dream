package com.dream.dubbo.application;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.dream.common.mapper")
@ComponentScan(basePackages = {
        "com.dream.dubbo.application.service",
        "com.dream.common.mapper",
        "com.dream.common.handler",
        "com.dream.dubbo.application.config"
})
@EnableDubbo
@EnableTransactionManagement
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(MainApplication.class, args);
        String[] names=context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("********************************************WEB启动完成***************************************");
    }

}
