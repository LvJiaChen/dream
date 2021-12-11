package com.dream.common.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lvxiaozuo
 * @date 2021/11/28 19:47
 */
public class CodeGenerator {

    public static void main(String[] args) {
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.controller, "E:\\IdeaProject\\dream\\dream-web\\src\\main\\java\\com\\dream\\web\\application\\Controller");
        pathInfo.put(OutputFile.mapper, "E:\\IdeaProject\\dream\\dream-mapper\\src\\main\\java\\com\\dream\\common\\mapper");
        pathInfo.put(OutputFile.serviceImpl, "E:\\IdeaProject\\dream\\dream-service\\src\\main\\java\\com\\dream\\service\\impl");
        pathInfo.put(OutputFile.service, "E:\\IdeaProject\\dream\\dream-service\\src\\main\\java\\com\\dream\\service");
        pathInfo.put(OutputFile.entity, "E:\\IdeaProject\\dream\\dream-mapper\\src\\main\\java\\com\\dream\\common\\entity");
        pathInfo.put(OutputFile.mapperXml, "E:\\IdeaProject\\dream\\dream-mapper\\src\\main\\resources\\mapper");
        FastAutoGenerator.create("jdbc:postgresql://10.10.20.132:5531/yasha_erp", "tpg_admin", "pG4tk8Rfd")
                .globalConfig(builder -> {
                    builder.author("lvxiaozuo") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E:\\IdeaProject\\dream\\dream-mapper\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.dream") // 设置父包名
                            .mapper("common.mapper") // 设置父包模块名
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("web.application.Controller")
                            .entity("common.entity")
                            .pathInfo(pathInfo);
                })
                .strategyConfig(builder -> {
                    builder.addInclude("dream_user") // 设置需要生成的表名
                            .addTablePrefix("dream_") // 设置过滤表前缀
                            .entityBuilder().enableLombok();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
