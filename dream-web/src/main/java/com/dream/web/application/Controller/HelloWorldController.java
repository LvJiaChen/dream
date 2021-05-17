package com.dream.web.application.Controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableAutoConfiguration
@Controller
@RequestMapping("HelloWorld")
public class HelloWorldController {

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        return "Hello world";
    }
}
