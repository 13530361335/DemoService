package com.joker;

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
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PreDestroy;
import java.net.InetAddress;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 */
@Slf4j
@MapperScan(Constant.PACKAGE_PATH_DAO)
@EnableAsync
@SpringBootApplication
public class Application  {



    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

