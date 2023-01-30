package com.joker.service.impl;

import com.joker.common.Constant;
import com.joker.common.Result;
import com.joker.controller.request.LoginParameter;
import com.joker.controller.request.LogoutParameter;
import com.joker.controller.request.RegisterParameter;
import com.joker.entity.User;
import com.joker.mapper.UserMapper;
import com.joker.service.AuthService;
import com.joker.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Joker Jing
 * @date 2019/7/29
 * @description
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * 验证码长度
     */
    private static final int VERIFICATION_CODE_LENGTH = 6;

    /**
     * 验证码过期时间
     */
    private static final long VERIFICATION_CODE_EXPIRE_TIME = 60;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result sendVerificationCode(String type, String account) {
        Object cacheVerificationCode = redisTemplate.opsForHash().get(Constant.REDIS_KEY_VERIFICATION_CODE, account);
        if (cacheVerificationCode != null) {
            return Result.fail(400, "60秒内不可重复发送验证码");
        }
        // 生成6位数字验证码
        String verificationCode = RandomStringUtils.randomNumeric(VERIFICATION_CODE_LENGTH);
        // 验证码存入redis,60秒失效
        redisTemplate.opsForHash().put(Constant.REDIS_KEY_VERIFICATION_CODE, account, verificationCode);
        redisTemplate.expire(Constant.REDIS_KEY_VERIFICATION_CODE, VERIFICATION_CODE_EXPIRE_TIME, TimeUnit.SECONDS);
        // 发送验证码
        try {
            if (VERIFY_TYPE_TELEPHONE.equals(type)) {
                // 手机验证
            } else {
                // 邮箱验证
                emailService.sendEmail(account, "验证码", verificationCode);
            }
        } catch (Exception e) {
            redisTemplate.opsForHash().delete(Constant.REDIS_KEY_VERIFICATION_CODE, account);
            log.info("发送验证码失败，account：{} type:{}", account, type);
            log.info(e.getMessage(), e);
            return Result.fail(500, "send email error");
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result register(RegisterParameter registerParameter) {
        String email = registerParameter.getEmail();
        String telephone = registerParameter.getTelephone();
        String verificationCode;
        User user = new User();
        // 识别注册类型
        if (!StringUtils.isEmpty(email)) {
            // 邮箱验证
            verificationCode = (String) redisTemplate.opsForHash().get(Constant.REDIS_KEY_VERIFICATION_CODE, email);
            user.setEmail(email);
            user.setAccount(email);
        } else {
            // 手机验证
            verificationCode = (String) redisTemplate.opsForHash().get(Constant.REDIS_KEY_VERIFICATION_CODE, telephone);
            user.setTelephone(telephone);
            user.setAccount(telephone);
        }

        // 校验验证码
        if (!registerParameter.getVerificationCode().equals(verificationCode)) {
            return Result.fail(403, "verification code is incorrect or expired");
        }

        user.setPassword("123456");
        userMapper.insert(user);
        return Result.success();
    }

    @Override
    public Result login(LoginParameter loginParameter) {
//        User user = userMapper.selectByAccount(loginParameter.getAccount());
//        if (user == null || !loginParameter.getPassword().equals(user.getPassword())) {
//            return Result.fail(403, "username or password error");
//        }
        return Result.success();
    }

    @Override
    public Result logout(LogoutParameter logoutParameter) {
        return null;
    }
}
