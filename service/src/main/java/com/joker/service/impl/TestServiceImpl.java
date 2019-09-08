package com.joker.service.impl;

import com.joker.common.Constant;
import com.joker.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Joker Jing
 * @date 2019/8/10
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Async
    @Override
    public void start(String taskId) {
        Long id = Thread.currentThread().getId();
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_TASK_THREAD, taskId, id);
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(1L);
                System.out.println(id);
            }
        } catch (InterruptedException e) {
            log.error("任务被打断", e.getMessage());
        }
    }

    @Override
    public void stop(String taskId) {
        Integer id = (Integer) redisTemplate.opsForHash().get(Constant.REDIS_KEY_TASK_THREAD, taskId);
        if (id == null) {
            log.warn("任务不存在:{}", taskId);
            return;
        }
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        allStackTraces.forEach((k, v) -> {
            if (id == k.getId()) {
                log.warn("{}:{}", k.getId(),k.getName());
                redisTemplate.opsForHash().delete(Constant.REDIS_KEY_TASK_THREAD, taskId);
                k.interrupt();
                log.warn("任务已手动终止:{}", taskId);
                return;
            }
        });
    }
}
