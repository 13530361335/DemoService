package com.joker;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.joker.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
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

@Slf4j
@MapperScan(Constant.PACKAGE_PATH_DAO)
@SpringBootApplication
public class Application implements ApplicationRunner {

    @Value("${server.port}")
    private int port;

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
            log.info("服务启动完成:http://{}:{}/swagger-ui.html", ip, port);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    /**
     * 服务关闭时执行
     */
    @PreDestroy
    public void destroy() {
        log.warn("服务已经关闭");
    }

    @Primary
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1.定义一个convert转换消息的对象
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        // 2.处理中文乱码
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(mediaTypes);

        // 3.设置fastJson配置
        FastJsonConfig jsonConfig = new FastJsonConfig();
        jsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        converter.setFastJsonConfig(jsonConfig);
        return new HttpMessageConverters(converter);
    }

}

