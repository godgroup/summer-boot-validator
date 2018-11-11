package com.summer.boot.validator;

import java.lang.reflect.Method;

/**
 * Created by lili on 2018/11/11.
 */
public class MethodInfo {
    private Method method;
    private  Class<?>[] paramTypes;
    private  String[] paramNames;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public String[] getParamNames() {
        return paramNames;
    }

    public void setParamNames(String[] paramNames) {
        this.paramNames = paramNames;
    }
}
