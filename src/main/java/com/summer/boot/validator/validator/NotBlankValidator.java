package com.summer.boot.validator.validator;


import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


@Component
public class NotBlankValidator extends AbstractAnnotationValidator<NotBlank> {

    @Override
    public ValidateResult validate(NotBlank notBlank, String paramName, Object paramValue) {

        if (!notBlank.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (paramValue == null
                || (paramValue instanceof String && notBlank.value() && StringUtils.isBlank((String) paramValue))) {
            String msg = notBlank.msg();
            if (StringUtils.isBlank(msg)) {
                msg = "参数验证不通过, " + paramName + "参数不能为空";
            }
            return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(NotBlank.class, this);
    }
}
