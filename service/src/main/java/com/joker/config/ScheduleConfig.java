package com.joker.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@Configuration
public class ScheduleConfig {

    @Scheduled(initialDelay = 1000, fixedRate = 1000 * 60 * 60)
    public void task() {
        log.info("1小时定时任务");
    }

}
