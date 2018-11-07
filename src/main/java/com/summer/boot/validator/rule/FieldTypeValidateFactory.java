package com.summer.boot.validator.rule;

import com.summer.boot.validator.BasicTypeValidate;
import com.summer.boot.validator.ValidateOperation;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* @description: 生产校验器
* @author:       yalunwang
* @createDate:  2018/11/6 16:02
* @version:      1.0
*/
public class FieldTypeValidateFactory {

    public final static Map<FiledTypeEnum,ValidateOperation> MAP=new ConcurrentHashMap<>();

    public static ValidateOperation getValidate(Class<?> paramClass) {
        FiledTypeEnum filedTypeEnum= getFiledTypeEnum(paramClass);
        return  MAP.get(filedTypeEnum);
    }

    private  static FiledTypeEnum getFiledTypeEnum (Class<?> paramClass){
        if(getIsBasicType(paramClass)){
            return FiledTypeEnum.Basic;
        }
        if(paramClass== List.class){
            return FiledTypeEnum.List;
        }
        return FiledTypeEnum.Bean;
    }

    protected static boolean getIsBasicType(Class<?> clazz) {

        return (
                clazz == Boolean.class ||
                        clazz == Character.class ||
                        clazz == String.class ||
                        clazz == Byte.class ||
                        clazz == Short.class ||
                        clazz == Integer.class ||
                        clazz == Long.class ||
                        clazz == Float.class ||
                        clazz == Double.class ||
                        clazz == BigDecimal.class
        );
    }
}
