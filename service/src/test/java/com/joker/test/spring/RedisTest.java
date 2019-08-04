package com.joker.test.spring;

import com.joker.common.Constant;
import com.joker.test.SpringBootBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author: Joker Jing
 * @date: 2019/8/4
 */
public class RedisTest extends SpringBootBaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test1() throws InterruptedException {
        redisTemplate.expire(Constant.REDIS_KEY_VERIFICATION_CODE, 20, TimeUnit.SECONDS);
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_VERIFICATION_CODE, "abc", "123456");
        TimeUnit.SECONDS.sleep(10);
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_VERIFICATION_CODE, "DEF", "QWE");
        TimeUnit.SECONDS.sleep(10);
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_VERIFICATION_CODE, "TYT", "GG");
    }

}
