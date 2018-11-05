package com.summer.boot.validator;

import java.lang.reflect.Method;


public interface ValidateOperation {

    ValidateResult validate(Method method, int paramIndex, Class<?> paramClass, String paramName, Object paramValue);
}
