package com.joker.test.spring;

import com.joker.common.Constant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author Joker Jing
 * @date 2019/8/4
 */
public class RedisTest extends BaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test() throws InterruptedException {
        redisTemplate.expire(Constant.REDIS_KEY_VERIFICATION_CODE, 20, TimeUnit.SECONDS);
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_VERIFICATION_CODE, "abc", "123456");
        TimeUnit.SECONDS.sleep(10);
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_VERIFICATION_CODE, "DEF", "QWE");

        redisTemplate.opsForZSet().add("a", "b", 1.2);
        redisTemplate.opsForZSet().add("a", "b", 1.3);
        redisTemplate.opsForZSet().add("a", "b", 1.1);
    }

}
