package com.joker.controller;

import com.joker.common.Constant;
import com.joker.common.Result;
import com.joker.common.exception.RequestException;
import com.joker.config.ExceptionConfig;
import com.joker.controller.request.LoginParameter;
import com.joker.controller.request.LogoutParameter;
import com.joker.controller.request.RegisterParameter;
import com.joker.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * created by Joker on 2019/7/16
 */
@Slf4j
@Api(tags = "授权接口")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("发送验证码")
    @GetMapping("sendVerificationCode")
    public Result sendVerificationCode(String type, String account) {
        // type：0，手机验证；1，邮箱验证。
        if (!"0".equals(type) && !"1".equals(type)) {
            return new Result<>(400, "type类型只能为0（手机验证）或1（邮箱验证）");
        }
        // 校验手机号码或邮箱。
        if (StringUtils.isEmpty(account)) {
            return new Result<>(400, "account不允许为空");
        }
        return authService.sendVerificationCode(type, account);
    }

    @ApiOperation("注册")
    @PostMapping("register")
    public Result register(@Validated @RequestBody RegisterParameter registerParameter, BindingResult bindingResult) {
        Result result = ExceptionConfig.checkParam(bindingResult);
        if (result.getCode() != 200) {
            return result;
        }
        return authService.register(registerParameter);
    }

    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(@Validated @RequestBody LoginParameter loginParameter, BindingResult bindingResult) {
        Result result = ExceptionConfig.checkParam(bindingResult);
        if (result.getCode() != 200) {
            return result;
        }
        return authService.login(loginParameter);
    }

    @ApiOperation("登出")
    @PostMapping("logout")
    public Result logout(@Validated @RequestBody LogoutParameter logoutParameter, BindingResult bindingResult) {
        Result result = ExceptionConfig.checkParam(bindingResult);
        if (result.getCode() != 200) {
            return result;
        }
        return authService.logout(logoutParameter);
    }

}