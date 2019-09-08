package com.joker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Joker Jing
 * @date 2019/7/29
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

    private static final String START_TIME = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

    private static final String PACKAGE_PATH_CONTROLLER = "com.joker.controller";

    @Value("${spring.application.name}")
    private String appName;

    /**
     * Swagger配置
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACKAGE_PATH_CONTROLLER))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(MessageFormat.format("服务名称: {0}", appName))
                        .description(MessageFormat.format("发布时间: {0}", START_TIME))
                        .version("1.0")
                        .build());
    }

}
