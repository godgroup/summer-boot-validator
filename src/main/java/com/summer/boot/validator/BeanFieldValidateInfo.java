package com.summer.boot.validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by lili on 2018/11/10.
 */
public class BeanFieldValidateInfo extends BasicTypeInfo {
    private Field field;
    private Method getMethod;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getGetMethod() {
        return getMethod;
    }

    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }
}
