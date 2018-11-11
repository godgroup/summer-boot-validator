package com.summer.boot.validator.rule;


import com.summer.boot.validator.config.ValidatorConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* @description: 生产校验器
* @author:       yalunwang
* @createDate:  2018/11/6 16:02
* @version:      1.0
*/
public abstract class AbstractValidateOperation  implements ValidateOperation{

    @Autowired
    ValidatorConfig validatorConfig;
    //存储不同参数类型对应的校验处理
    public final static Map<FiledTypeEnum,ValidateOperation> MAP=new HashMap<>();

    protected static void register(FiledTypeEnum filedTypeEnum, ValidateOperation validateOperation){
        MAP.putIfAbsent(filedTypeEnum,validateOperation);
    }

    public static ValidateOperation getValidate(Class<?> paramClass) {
        FiledTypeEnum filedTypeEnum= getFiledTypeEnum(paramClass);
        return  MAP.get(filedTypeEnum);
    }

    /**
     * 返回参数类型对赢得枚举
     * @param paramClass
     * @return
     */
    private  static FiledTypeEnum getFiledTypeEnum (Class<?> paramClass){
        if(getIsBasicType(paramClass)){
            return FiledTypeEnum.Basic;
        }
        if(paramClass== List.class){
            return FiledTypeEnum.List;
        }
        return FiledTypeEnum.Bean;
    }

    /**
     * 判断是否是基础类型
     * @param clazz
     * @return
     */
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
    public int getErrorCode() {
        return validatorConfig.getErrorCode();
    }
}
