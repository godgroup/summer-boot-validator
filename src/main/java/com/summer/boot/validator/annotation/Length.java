package com.summer.boot.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Length {

    int max() default Integer.MAX_VALUE;

    int min() default 0;

    String msg() default "";

    boolean enable() default true;

    boolean skipEmpty() default false;
}
