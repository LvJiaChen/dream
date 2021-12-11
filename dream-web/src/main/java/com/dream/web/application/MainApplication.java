package com.dream.web.application;

import com.dream.web.application.config.MyBatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.dream.common.mapper")
@ComponentScan(basePackages = {
        "com.dream.service",
        "com.dream.web.application.Controller",
        "com.dream.common.mapper",
        "com.dream.web.application.config"
})
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(MainApplication.class, args);
        String[] names=context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        MyBatisPlusConfig myBatisPlusConfig= context.getBean(MyBatisPlusConfig.class);
        MyBatisPlusConfig myBatisPlusConfig1= context.getBean(MyBatisPlusConfig.class);
        System.out.println(myBatisPlusConfig);
        System.out.println(myBatisPlusConfig1);
        System.out.println(myBatisPlusConfig==myBatisPlusConfig1);
        System.out.println("********************************************WEB启动完成***************************************");
    }

}
