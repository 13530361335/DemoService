package com.joker.common.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class HandlingTimeAspect {

    @Pointcut("@annotation(com.joker.common.annotation.HandlingTime)")
    public void handlingTimePointcut() {
    }

    @Around("handlingTimePointcut()")
    public Object handlingTimeAround(ProceedingJoinPoint joinPoint) {
        try {
            long startTime = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            log.info("方法执行时间：" + (System.currentTimeMillis() - startTime));
            return proceed;
        } catch (Throwable throwable) {
            log.info(throwable.getMessage(), throwable);
        }
        return null;
    }
}