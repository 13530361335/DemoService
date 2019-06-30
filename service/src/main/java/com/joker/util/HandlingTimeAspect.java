package com.joker.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HandlingTimeAspect {

    @Pointcut("@annotation(com.joker.annotation.HandlingTime)")
    public void handlingTimePointcut() {
    }

    @Around("handlingTimePointcut()")
    public Object handlingTimeAround(ProceedingJoinPoint joinPoint) {
        try {
            long startTime = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            System.out.println(proceed);
            System.out.println("方法执行时间：" + (System.currentTimeMillis() - startTime));
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}