package com.summer.boot.validator;

import com.summer.boot.validator.validator.AbstractAnnotationValidator;
import java.lang.annotation.Annotation;

/**
 *  包装注解对应的校验器
 */
public class AnnotationValidatorWrapper {
    private Annotation annotation;
    private AbstractAnnotationValidator validator;

    public AnnotationValidatorWrapper(Annotation annotation, AbstractAnnotationValidator validator) {
        this.annotation = annotation;
        this.validator = validator;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public AbstractAnnotationValidator getValidator() {
        return validator;
    }

    public void setValidator(AbstractAnnotationValidator validator) {
        this.validator = validator;
    }
}
