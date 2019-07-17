package com.joker.controller;

import com.joker.common.Result;
import com.joker.config.ExceptionConfig;
import com.joker.controller.request.RequestDemo;
import com.joker.controller.response.ResponseDemo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Joker on 2019/7/12
 */
@Api(tags = "demo接口")
@RestController
@RequestMapping("demo")
public class DemoController {

    @ApiOperation("方法名")
    @PostMapping("hello")
    public Result<ResponseDemo> hello(@Validated @RequestBody RequestDemo requestDemo, BindingResult bindingResult) {
        Result result = ExceptionConfig.checkParam(bindingResult);
        if (result.getCode() != 200) {
            return result;
        }
        return new Result<>();
    }

}