package com.summer.boot.validator.validator;


import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.Max;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


@Component
public class MaxValidator extends AbstractAnnotationValidator<Max> {

    @Override
    public ValidateResult validate(Max max, String paramName, Object paramValue) {

        if (!max.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (paramValue != null) {
            Class<?> valueClass = paramValue.getClass();
            if ((valueClass == Byte.class || valueClass == Byte.TYPE)
                    || (valueClass == Short.class || valueClass == Short.TYPE)
                    || (valueClass == Integer.class || valueClass == Integer.TYPE)
                    || (valueClass == Long.class || valueClass == Long.TYPE)) {

                Long value = Long.valueOf(paramValue.toString());
                if (value > max.value()) {
                    String msg = max.msg();
                    if (StringUtils.isBlank(msg)) {
                        msg = "参数验证不通过, " + paramName + "大于最大值" + max.value();
                    }
                    return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
                }
            }
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(Max.class, this);
    }
}
