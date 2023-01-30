package com.joker.config;

import com.joker.service.EmailService;
import com.joker.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * @author Joker Jing
 * @date 2019/8/18
 */
@Slf4j
@Configuration
public class ApplicationRunnerConfig implements ApplicationRunner {

    private static final String OS = System.getProperty("os.name");
    private static final String JDK = System.getProperty("java.version");
    private static final String ACTIVE_DEV = "dev";

    @Value("${server.port}")
    private int port;

    @Value("${spring.profiles.active}")
    private String active;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Autowired
    private EmailService emailService;

    /**
     * 服务启动后执行
     */
    @Override
    public void run(ApplicationArguments args) {
        try {
            String ip = IpUtil.getIpAddress();
            log.info("JDK: {} [{}] ", JDK, OS);
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
        if (!ACTIVE_DEV.equals(active)) {
            emailService.sendEmail(adminEmail, "服务关闭", "你好，服务已关闭");
        }
    }

}
