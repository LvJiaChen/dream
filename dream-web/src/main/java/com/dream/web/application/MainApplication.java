package com.dream.web.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.dream.common.mapper")
@ComponentScan( basePackages = {
"com.dream.service",
})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
        System.out.println("********************************************WEB启动完成***************************************");
    }

}
