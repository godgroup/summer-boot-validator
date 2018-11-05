package com.summer.boot.validator.validator;

import com.summer.boot.validator.ValidateResult;
import org.springframework.beans.factory.InitializingBean;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractAnnotationValidator<T> implements InitializingBean {

    public final static Map<Class<? extends Annotation>, AbstractAnnotationValidator> ANNOTATION_VALIDATOR_MAP=new ConcurrentHashMap<>();

    /**
     * 注册注解对应的验证器
     * @param annotationType
     * @param abstractAnnotationValidator
     */
    protected void registerValidator(Class<? extends Annotation> annotationType, AbstractAnnotationValidator abstractAnnotationValidator){
        ANNOTATION_VALIDATOR_MAP.putIfAbsent(annotationType,abstractAnnotationValidator);
    }

    /**
     * 获取注解对应的验证器
     * @param annotationType
     * @return
     */
    public static AbstractAnnotationValidator getAnnotationValidator(Class<? extends Annotation> annotationType){
        return ANNOTATION_VALIDATOR_MAP.get(annotationType);
    }

    public   abstract  ValidateResult validate(T t, String paramName, Object paramValue);
}
