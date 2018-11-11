package com.summer.boot.validator.rule;

import com.summer.boot.validator.AnnotationValidatorWrapper;
import com.summer.boot.validator.BasicTypeInfo;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.validator.AbstractAnnotationValidator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
* @description: 基本数据校验器(Long String等)
* @author:       yalunwang
* @createDate:  2018/11/6 15:56
* @version:      1.0
*/
@Component
public class BasicTypeValidate extends AbstractValidateOperation implements  InitializingBean {

    private final static Map<Method,Map<Integer,BasicTypeInfo>> CACEMAP=new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings(value = "unchecked")
    public ValidateResult validate(Method method, int paramIndex, Class<?> paramClass, String paramName, Object paramValue) {

        BasicTypeInfo basicTypeInfo=getBasicTypeInfo(method,paramIndex,paramClass,paramName);

        for (AnnotationValidatorWrapper annotationValidatorWrapper : basicTypeInfo.getAnnotationValidatorWrapperList()) {
            ValidateResult  result = annotationValidatorWrapper.getValidator()
                    .validate(annotationValidatorWrapper.getAnnotation(), paramName, paramValue);
            if (!result.isValid()) {
                return  result;
            }
        }
        return ValidateResult.SUCCESS;
    }

    /**
     * 获取 获取方法上的第 paramIndex的BasicTypeInfo
     * @param method
     * @param paramIndex
     * @param paramClass
     * @param paramName
     * @return
     */
    private BasicTypeInfo getBasicTypeInfo(Method method, int paramIndex, Class<?> paramClass, String paramName){
        BasicTypeInfo basicTypeInfo = new BasicTypeInfo();
        basicTypeInfo.setMethod(method);
        basicTypeInfo.setParamIndex(paramIndex);
        basicTypeInfo.setParamName(paramName);
        basicTypeInfo.setParamType(paramClass);

        Map<Integer,BasicTypeInfo> basicTypeInfoMapMap =CACEMAP.get(method);
        if (basicTypeInfoMapMap==null){
            basicTypeInfo.setAnnotationValidatorWrapperList(getAnnotationValidatorWrapperList(method, paramIndex));
            basicTypeInfoMapMap=new HashMap<>();
            basicTypeInfoMapMap.putIfAbsent(paramIndex,basicTypeInfo);
            CACEMAP.putIfAbsent(method,basicTypeInfoMapMap);
            return basicTypeInfo;
        }
        else{
            if(basicTypeInfoMapMap.get(paramIndex)==null){
                basicTypeInfo.setAnnotationValidatorWrapperList(getAnnotationValidatorWrapperList(method, paramIndex));
                basicTypeInfoMapMap.putIfAbsent(paramIndex,basicTypeInfo);
                return basicTypeInfo;
            }
            else {
                return basicTypeInfoMapMap.get(paramIndex);
            }
        }

    }

    /**
     * 获取方法上的第 paramIndex的注解List
     * @param method
     * @param paramIndex
     * @return
     */
    private  List<AnnotationValidatorWrapper> getAnnotationValidatorWrapperList(Method method, int paramIndex){
        List<AnnotationValidatorWrapper> annotationValidatorWrapperList=new ArrayList<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation annotation : parameterAnnotations[paramIndex]) {
            AbstractAnnotationValidator validator=AbstractAnnotationValidator.getAnnotationValidator(annotation.annotationType());
            if(validator!=null){
                AnnotationValidatorWrapper annotationValidatorWrapper = new AnnotationValidatorWrapper(annotation,validator);
                annotationValidatorWrapperList.add(annotationValidatorWrapper);
            }
        }
        return annotationValidatorWrapperList;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        //注册基本类型的校验器
        AbstractValidateOperation.MAP.put(FiledTypeEnum.Basic,this);
    }
}
