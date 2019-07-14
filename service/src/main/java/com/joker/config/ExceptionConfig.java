package com.joker.config;

import com.joker.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Configuration
public class ExceptionConfig implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        log.error("请求发生异常:{}", request.getRequestURI());
        log.error(e.getMessage(), e);
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        mv.addObject("code", 500);
        mv.addObject("message", "服务器出错了!");
        mv.addObject("result", "");
        return mv;
    }

    public static Result checkParam(BindingResult bindingResult) {
        if (bindingResult.getErrorCount() == 0) {
            return new Result();
        }
        List<String> list = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            list.add(field + message);
        });
        return new Result(400, "参数校验不通过", list);
    }

}