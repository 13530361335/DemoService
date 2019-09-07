package com.joker.config;

import com.alibaba.fastjson.JSONException;
import com.joker.common.Result;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Joker Jing
 * @date: 2019/9/5
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionConfig {

    @ExceptionHandler(JSONException.class)
    public Result<?> jsonException(JSONException e) {
        return Result.fail(500, e.getMessage());
    }

    public static Result checkParam(BindingResult bindingResult) {
        if (bindingResult.getErrorCount() == 0) {
            return Result.success();
        }
        List<String> errorList = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorList.add(field + message);
        });
        return Result.getInstance(400, "parameter no pass", errorList);
    }

}
