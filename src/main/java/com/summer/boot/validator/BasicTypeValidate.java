package com.summer.boot.validator;

import com.summer.boot.validator.rule.FieldTypeValidateFactory;
import com.summer.boot.validator.rule.FiledTypeEnum;
import com.summer.boot.validator.validator.AbstractAnnotationValidator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
* @description: 基本数据校验器(Long String等)
* @author:       yalunwang
* @createDate:  2018/11/6 15:56
* @version:      1.0
*/
@Component
public class BasicTypeValidate implements ValidateOperation, InitializingBean {

    @Override
    @SuppressWarnings(value = "unchecked")
    public ValidateResult validate(Method method, int paramIndex, Class<?> paramClass, String paramName, Object paramValue) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        BasicTypeInfo basicTypeInfo = new BasicTypeInfo();
        basicTypeInfo.setMethod(method);
        basicTypeInfo.setParamIndex(paramIndex);
        basicTypeInfo.setParamName(paramName);
        basicTypeInfo.setParamType(paramClass);
        List<AnnotationValidatorWrapper> annotationValidatorWrapperList = new ArrayList<>();
        for (Annotation annotation : parameterAnnotations[paramIndex]) {
            AnnotationValidatorWrapper annotationValidatorWrapper = new AnnotationValidatorWrapper(annotation,
                    AbstractAnnotationValidator.getAnnotationValidator(annotation.annotationType()));
            annotationValidatorWrapperList.add(annotationValidatorWrapper);
        }
        basicTypeInfo.setAnnotationValidatorWrapperList(annotationValidatorWrapperList);
        for (AnnotationValidatorWrapper annotationValidatorWrapper : basicTypeInfo.getAnnotationValidatorWrapperList()) {
            ValidateResult  result = annotationValidatorWrapper.getValidator()
                    .validate(annotationValidatorWrapper.getAnnotation(), paramName, paramValue);
            if (!result.isValid()) {
                return  result;
            }
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //注册基本类型的校验器
        FieldTypeValidateFactory.MAP.putIfAbsent(FiledTypeEnum.Basic,this);
    }
}
