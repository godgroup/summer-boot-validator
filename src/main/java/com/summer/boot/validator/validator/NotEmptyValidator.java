package com.summer.boot.validator.validator;


import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.NotEmpty;
import jdk.nashorn.internal.objects.NativeUint8Array;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


@Component
public class NotEmptyValidator extends AbstractAnnotationValidator<NotEmpty> {

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(NotEmpty.class,this);
    }

    /**
     * 校验实现
     * @param notEmpty
     * @param paramName
     * @param paramValue
     * @return
     */
    @Override
    public ValidateResult validate(NotEmpty notEmpty, String paramName, Object paramValue) {
        //如果没有启用验证,直接通过
        if(!notEmpty.enable()){
            return ValidateResult.SUCCESS;
        }
        if(paramName==null || (paramValue instanceof String && StringUtils.isEmpty((String)paramValue))){
            String msg=notEmpty.msg();
            if(StringUtils.isBlank(msg)){
                msg="参数校验失败,参数:"+paramName+"不能为空";
            }

           return ValidateResult.ERROR(22, new ErrorDetail(paramName,paramValue,msg));
        }
        return ValidateResult.SUCCESS;
    }
}
