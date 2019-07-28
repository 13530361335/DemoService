package com.joker.service;

import com.joker.common.Result;
import com.joker.common.exception.RequestException;
import com.joker.controller.request.LoginParameter;
import com.joker.controller.request.LogoutParameter;
import com.joker.controller.request.RegisterParameter;

/**
 * @auther: Joker Jing
 * @Date: 2019/7/29
 * @Description:
 */
public interface AuthService {

    /**
     * 手机验证
     */
    String VERIFY_TYPE_TELEPHONR = "0";

    /**
     * 邮箱验证
     */
    String VERIFY_TYPE_EMAIL = "1";

    /**
     * 发送验证码
     * @param type
     * @param account
     * @return
     */
    Result sendVerificationCode(String type, String account);

    /**
     * 注册
     * @param registerParameter
     * @return
     */
    Result register(RegisterParameter registerParameter);

    /**
     * 登录
     * @param loginParameter
     * @return
     */
    Result login(LoginParameter loginParameter);

    /**
     * 登出
     * @param logoutParameter
     * @return
     */
    Result logout(LogoutParameter logoutParameter);

}
