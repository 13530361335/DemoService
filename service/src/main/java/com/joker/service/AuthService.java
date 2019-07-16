package com.joker.service;

import com.joker.common.Result;
import com.joker.common.exception.RequestException;
import com.joker.controller.request.LoginParameter;
import com.joker.controller.request.LogoutParameter;
import com.joker.controller.request.RegisterParameter;

/**
 * created by Joker on 2019/7/16
 */
public interface AuthService {

    /**
     * 发送验证码
     */
    Result sendVerificationCode(String type, String account);

    /**
     * 注册
     */
    Result register(RegisterParameter registerParameter);

    /**
     * 登录
     */
    Result login(LoginParameter loginParameter);

    /**
     * 登出
     */
    Result logout(LogoutParameter logoutParameter);

}
