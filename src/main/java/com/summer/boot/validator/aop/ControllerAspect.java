package com.summer.boot.validator.aop;


import com.summer.boot.validator.MethodInfo;
import com.summer.boot.validator.config.ValidatorConfig;
import com.summer.boot.validator.rule.ValidateOperation;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.exception.ValidateRuntimeException;
import com.summer.boot.validator.rule.AbstractValidateOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
* @description: 控制器切面
* @author:      yalunwang
* @createDate:  2018/11/01 15:21
* @version:     1.0
*/
@Aspect
@Component
@Order(-1)
public class ControllerAspect {

    @Autowired
    ValidatorConfig validatorConfig;
    private static final ConcurrentHashMap<Method, MethodInfo> METHODINFO_MAP = new ConcurrentHashMap<>();
    /**
     * 拦截 @RequestMapping
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        if(!validatorConfig.isEnable()){
            return joinPoint.proceed();
        }

        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();

        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();//目标方法

        MethodInfo methodInfo=METHODINFO_MAP.get(targetMethod);
        if (methodInfo==null){
            Class<?>[] paramTypes = targetMethod.getParameterTypes();
            String[] paramNames = methodSignature.getParameterNames();
            methodInfo = new MethodInfo();
            methodInfo.setMethod(targetMethod);
            methodInfo.setParamTypes(paramTypes);
            methodInfo.setParamNames(paramNames);
            METHODINFO_MAP.putIfAbsent(targetMethod,methodInfo);
        }

        for (int i = 0; i < methodInfo.getParamTypes().length; i++) {
            ValidateOperation validate = AbstractValidateOperation.getValidate(methodInfo.getParamTypes()[i]);
            ValidateResult validateResult = validate.validate(targetMethod, i, methodInfo.getParamTypes()[i],methodInfo.getParamNames()[i], args[i]);
            if (!validateResult.isValid()) {
                throw new ValidateRuntimeException(validateResult.getCode(), validateResult.getErrorDetail());
            }
        }
        return joinPoint.proceed();
    }
}
