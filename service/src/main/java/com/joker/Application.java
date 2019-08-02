package com.joker;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.joker.common.Constant;
import com.joker.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 */
@Slf4j
@MapperScan(Constant.PACKAGE_PATH_DAO)
@SpringBootApplication
public class Application implements ApplicationRunner {

    @Value("${server.port}")
    private int port;

    @Value("${spring.profiles.active}")
    private String active;

    @Value("${admin.email}")
    private String adminEmail;

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 服务启动后执行
     */
    @Override
    public void run(ApplicationArguments args) {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            log.info("http://{}:{}/swagger-ui.html", ip, port);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    /**
     * 服务关闭时执行
     */
    @PreDestroy
    public void destroy() throws Exception {
        log.warn("服务已经关闭");
        if (!Constant.APPLICATION_ACTIVE_DEV.equals(active)) {
            emailService.sendEmail(adminEmail, "服务关闭", "你好，服务已关闭");
        }
    }

    @Primary
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1.FastJson配置：空值字段保留
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);

        // 2.设置UTF-8编码
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        // 3.定义一个convert转换消息的对象
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(mediaTypes);
        return new HttpMessageConverters(converter);
    }

}

