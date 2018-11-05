package com.summer.boot.validator.aop;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * desc: 控制器切面
 * author: shenxy
 * date: 2018/10/25
 */
@Aspect
@Component
@Order(-1)
public class ControllerAspect  {
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();

        System.out.println("ssnimeiienru");


       return joinPoint.proceed();

    }
}
