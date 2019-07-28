package com.joker.service.impl;

import com.joker.common.Constant;
import com.joker.common.Result;
import com.joker.common.exception.RequestException;
import com.joker.controller.request.LoginParameter;
import com.joker.controller.request.LogoutParameter;
import com.joker.controller.request.RegisterParameter;
import com.joker.service.AuthService;
import com.joker.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @auther: Joker Jing
 * @Date: 2019/7/29
 * @Description:
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailService emailService;

    @Override
    public Result sendVerificationCode(String type, String account) {
        Object cacheVerificationCode = redisTemplate.opsForHash().get(Constant.REDIS_KEY_VERIFICATION_CODE, account);
        if (cacheVerificationCode != null) {
            return new Result<>(400, "60秒内不可重复发送验证码");
        }
        // 生成6位数字验证码
        String verificationCode = RandomStringUtils.randomNumeric(6);
        log.info("生成验证码:{}", verificationCode);
        // 验证码存入redis,60秒失效
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_VERIFICATION_CODE, account, verificationCode);
        redisTemplate.expire(Constant.REDIS_KEY_VERIFICATION_CODE, 10, TimeUnit.SECONDS);
        // 发送验证码
        try {
            if (VERIFY_TYPE_TELEPHONR.equals(type)) {
                // 手机验证
                log.info("发送验证码到手机号:{}", account);
            } else {
                // 邮箱验证
                log.info("发送验证码到邮箱:{}", account);
                emailService.sendEmail(account, "验证码", verificationCode);
            }
        } catch (Exception e) {
            redisTemplate.opsForHash().delete(Constant.REDIS_KEY_VERIFICATION_CODE, account);
            log.info("发送验证码失败，account：{} type:{}", account, type);
            log.info(e.getMessage(), e);
            return new Result<>(500, "邮件发送失败");
        }
        return new Result<>();
    }

    @Override
    public Result register(RegisterParameter registerParameter) {
        // 识别注册类型

        // 发送验证码
        return null;
    }

    @Override
    public Result login(LoginParameter loginParameter) {
        return null;
    }

    @Override
    public Result logout(LogoutParameter logoutParameter) {
        return null;
    }
}
