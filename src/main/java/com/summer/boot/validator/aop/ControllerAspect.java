package com.summer.boot.validator.aop;


import com.summer.boot.validator.ValidateOperation;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.config.SignConfig;
import com.summer.boot.validator.exception.ValidateRuntimeException;
import com.summer.boot.validator.rule.FieldTypeValidateFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * desc: 控制器切面
 * author: shenxy
 * date: 2018/10/25
 */
@Aspect
@Component
@Order(-1)
public class ControllerAspect {

    @Autowired
    SignConfig signConfig;

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println(signConfig.getSecret());
        System.out.println(signConfig.getAppId());
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();//目标方法
        Class<?>[] paramTypes = targetMethod.getParameterTypes();
        String[] paramNames = methodSignature.getParameterNames();
        for (int i = 0; i < paramTypes.length; i++) {
            ValidateOperation validate = FieldTypeValidateFactory.getValidate(paramTypes[i]);
            ValidateResult validateResult = validate.validate(targetMethod, i, paramTypes[i], paramNames[i], args[i]);
            if (!validateResult.isValid()) {
                throw new ValidateRuntimeException(validateResult.getCode(), validateResult.getFailedReason());
            }
        }
        return joinPoint.proceed();
    }
}
