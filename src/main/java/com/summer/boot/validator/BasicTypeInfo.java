package com.summer.boot.validator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 基础数据类型参数详情
 * @author:      yalunwang
 * @createDate:  2018/11/01 16:02
 * tips:
 * 参数校验的维度
 * method-->paramIndex(校验属性：annotationValidatorWrapperList)
 *             -->paramType
 *             -->paramName
 */
public class BasicTypeInfo {
    private Method method;
    private int paramIndex;
    private Class<?> paramType;
    private String paramName;
    //对应的注解及其对于的校验器
    private List<AnnotationValidatorWrapper> annotationValidatorWrapperList = new ArrayList<>();

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(int paramIndex) {
        this.paramIndex = paramIndex;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public List<AnnotationValidatorWrapper> getAnnotationValidatorWrapperList() {
        return annotationValidatorWrapperList;
    }

    public void setAnnotationValidatorWrapperList(List<AnnotationValidatorWrapper> annotationValidatorWrapperList) {
        this.annotationValidatorWrapperList = annotationValidatorWrapperList;
    }
}
