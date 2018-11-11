package com.summer.boot.validator.rule;

import com.summer.boot.validator.ValidateResult;

import java.lang.reflect.Method;


public interface ValidateOperation {
    /**
     *  执行校验
     * @param method
     * @param paramIndex
     * @param paramClass
     * @param paramName
     * @param paramValue
     * @return
     */
    ValidateResult validate(Method method, int paramIndex, Class<?> paramClass, String paramName, Object paramValue);
}
