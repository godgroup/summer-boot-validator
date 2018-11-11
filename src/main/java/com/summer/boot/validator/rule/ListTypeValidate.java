package com.summer.boot.validator.rule;

import com.summer.boot.validator.ValidateResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lili on 2018/11/11.
 */
@Component
public class ListTypeValidate extends AbstractValidateOperation implements InitializingBean {

    @Override
    public ValidateResult validate(Method method, int paramIndex, Class<?> paramClass, String paramName, Object paramValue) {
        ValidateOperation validate=  AbstractValidateOperation.MAP.get(FiledTypeEnum.Bean);
        List list=(List)paramValue;
        if(list.size()<1){
            return ValidateResult.SUCCESS;
        }
        Class<?> clazz = list.get(0).getClass();
        for (Object item :list){
            ValidateResult result = validate.validate(method,paramIndex,clazz,paramName,item);
            if(!result.isValid()){
                return result;
            }
        }

        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        register(FiledTypeEnum.List,this);
    }

//    public static void main(String[] args){
////        Type t=new ListTypeValidate<String>().getClass();
////        //获取泛型参数的实际类型
////        System.out.println(((ParameterizedType)t).getActualTypeArguments()[0]);
//
//
//        ParameterizedType parameterizedType = (ParameterizedType) new ArrayList<>().getClass().getGenericSuperclass();//获取当前new对象的泛型的父类类型
//
//        int index = 0;//第n个泛型    Map<K,V> 就有2个  拿K  就是0  V就是1
//
//
//        Class clazz = (Class<T>) parameterizedType.getActualTypeArguments()[index];
//        System.out.println("clazz ==>> "+clazz);
//    }
}
