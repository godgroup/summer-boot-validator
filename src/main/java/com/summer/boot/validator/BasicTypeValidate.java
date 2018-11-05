package com.summer.boot.validator;

import com.summer.boot.validator.validator.AbstractAnnotationValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BasicTypeValidate implements ValidateOperation {

    @Override
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
        for (AnnotationValidatorWrapper annotationValidatorWrapper : basicTypeInfo.getAnnotationValidatorWrapperList()) {
            ValidateResult  result = annotationValidatorWrapper.getValidator()
                    .validate(annotationValidatorWrapper.getAnnotation(), paramName, paramValue);
            if (!result.isValid()) {
                return  result;
            }
        }
        return ValidateResult.SUCCESS;
    }
}
