package com.joker.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
public class ExceptionConfig implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        log.error("请求发生异常:{}", request.getRequestURI());
        log.error(e.getMessage(), e);
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        mv.addObject("code", 500);
        mv.addObject("message", "服务器出错了!");
        mv.addObject("result", "");
        return mv;
    }

}