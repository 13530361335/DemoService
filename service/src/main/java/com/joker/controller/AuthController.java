package com.joker.controller;

import com.joker.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Joker on 2019/7/16
 */
@Api(tags = "授权接口")
@RestController
@RequestMapping("auth")
public class AuthController {

    @ApiOperation("注册")
    @PostMapping("register")
    public Result register(){
        return null;
    }

    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(){
        return null;
    }

    @ApiOperation("登出")
    @PostMapping("logout")
    public Result logout(){
        return null;
    }

}
