package com.summer.boot.validator.validator;

import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


/**
 *  Null检测
 */
@Component
public class NotNullValidator extends AbstractAnnotationValidator<NotNull> {

    @Override
    public ValidateResult validate(NotNull notNull, String paramName, Object paramValue) {

        if (!notNull.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (notNull.value() && paramValue == null) {
            String msg = notNull.msg();
            if (StringUtils.isBlank(msg)) {
                msg = "参数验证不通过, " + paramName + " 不能为空";
            }
            return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(NotNull.class, this);
    }
}
