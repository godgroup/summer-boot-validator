package com.summer.boot.validator.rule;

import com.summer.boot.validator.AnnotationValidatorWrapper;
import com.summer.boot.validator.BeanFieldValidateInfo;
import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.validator.AbstractAnnotationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* @description: Bean类的校验规则
* @author:      yalunwang
* @createDate:  2018/11/10 23:28
* @version:     1.0
*/
@Component
public class BeanTypeValidate extends AbstractValidateOperation implements  InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanTypeValidate.class);

    private final static Map<Class<?>,List<BeanFieldValidateInfo>> CACHEMAP = new ConcurrentHashMap<>();

    @Override
    public ValidateResult validate(Method method, int paramIndex, Class<?> paramClass, String paramName, Object paramValue) {

        List<BeanFieldValidateInfo> beanFieldValidateInfoList = CACHEMAP.get(paramClass);
        if (beanFieldValidateInfoList==null){
            beanFieldValidateInfoList=resolveBeanFieldValidator(method, paramIndex, paramClass, paramName);
        }
        for (BeanFieldValidateInfo beanFieldValidateInfo : beanFieldValidateInfoList) {
            List<AnnotationValidatorWrapper> validatorWrapperList = beanFieldValidateInfo.getAnnotationValidatorWrapperList();
            for (AnnotationValidatorWrapper validatorWrapper : validatorWrapperList) {

                Field field = beanFieldValidateInfo.getField();
                Annotation annotation = validatorWrapper.getAnnotation();

                AbstractAnnotationValidator annotationValidator = validatorWrapper.getValidator();

                if (annotationValidator != null) {
                    try {
                        ValidateResult result = annotationValidator.validate(annotation, field.getName(),beanFieldValidateInfo.getGetMethod().invoke(paramValue));
                        if (!result.isValid()) {
                            return result;
                        }
                    } catch (Exception e) {
                        String errMsg = "Can not validate field " + field.getName()
                                + " in class " + paramClass.getName()
                                + " with annotation " + annotation.annotationType().getName();
                       return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, errMsg));
                    }
                }
            }
        }
        return ValidateResult.SUCCESS;
    }
    private List<BeanFieldValidateInfo> resolveBeanFieldValidator(Method method, int paramIndex, Class<?> clazz, String paramName) {

        List<BeanFieldValidateInfo> beanFieldValidateInfoList = null;
        if (beanFieldValidateInfoList != null) {
            return beanFieldValidateInfoList;
        } else {
            beanFieldValidateInfoList = new ArrayList<>();
            Class<?> superClazz = clazz;
            while (superClazz!=null&&superClazz != Object.class) {
                Field[] fields = superClazz.getDeclaredFields();
                for (Field field : fields) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    BeanFieldValidateInfo beanFieldValidateInfo = resolveFieldValidator(superClazz, field);
                    beanFieldValidateInfo.setMethod(method);
                    beanFieldValidateInfo.setParamIndex(paramIndex);
                    beanFieldValidateInfo.setParamType(clazz);
                    beanFieldValidateInfo.setParamName(paramName);
                    if (beanFieldValidateInfo.getAnnotationValidatorWrapperList().size() > 0) {
                        beanFieldValidateInfoList.add(beanFieldValidateInfo);
                    }
                }
                superClazz = superClazz.getSuperclass();
            }
            CACHEMAP.putIfAbsent(clazz,beanFieldValidateInfoList);
            return beanFieldValidateInfoList;
        }
    }

    private BeanFieldValidateInfo resolveFieldValidator(Class<?> clazz, Field field) {
        BeanFieldValidateInfo beanFieldValidateInfo = new BeanFieldValidateInfo();
        beanFieldValidateInfo.setField(field);
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz) ;
            //得到name属性的getter方法
            Method readMethod = pd.getReadMethod() ;
            //获取filed的get的方法
            beanFieldValidateInfo.setGetMethod(readMethod);
        } catch (Exception e) {
            LOGGER.error("Can not find get method for field " + field.getName()
                    + " in class " + clazz.getName(), e);
            return beanFieldValidateInfo;
        }
        List<AnnotationValidatorWrapper> validatorWrappers = new ArrayList<>();
        for (Annotation annotation : field.getAnnotations()) {
            AbstractAnnotationValidator validator = AbstractAnnotationValidator.getAnnotationValidator(annotation.annotationType());
            if (validator != null) {
                validatorWrappers.add(new AnnotationValidatorWrapper(annotation, validator));
            }
        }
        beanFieldValidateInfo.setAnnotationValidatorWrapperList(validatorWrappers);
        return beanFieldValidateInfo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //注册bean类型的校验器
        AbstractValidateOperation.MAP.put(FiledTypeEnum.Bean, this);
    }
}
