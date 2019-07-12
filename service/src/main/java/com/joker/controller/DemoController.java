package com.joker.controller;

import com.joker.common.Result;
import com.joker.controller.request.RequestDemo;
import com.joker.controller.response.ResponseDemo;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * created by Joker on 2019/7/12
 */
@Validated
@RestController
@RequestMapping("demo")
public class DemoController {

    @PostMapping("hello")
    public Result<ResponseDemo> hello(@Valid @RequestBody RequestDemo requestDemo) {
        return new Result<>();
    }

}
