package com.summer.boot.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Decimal {

    int scale() default 2;

    int numLength() default -1;

    double min() default Double.MIN_VALUE * -1;

    double max() default Double.MAX_VALUE;

    boolean enable() default true;
}
