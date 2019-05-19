package com.summer.boot.validator.validator;


import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.Length;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 长度校验
 */
@Component
public class LengthValidator extends AbstractAnnotationValidator<Length> {

    @Override
    public ValidateResult validate(Length length, String paramName, Object paramValue) {

        if (!length.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (paramValue != null &&
                (paramValue.getClass() == String.class
                        || paramValue.getClass() == Byte.class || paramValue.getClass() == Byte.TYPE
                        || paramValue.getClass() == Short.class || paramValue.getClass() == Short.TYPE
                        || paramValue.getClass() == Integer.class || paramValue.getClass() == Integer.TYPE
                        || paramValue.getClass() == Long.class || paramValue.getClass() == Long.TYPE)) {

            String v = String.valueOf(paramValue);

            if ("".equals(paramValue) && length.skipEmpty()) {
                return ValidateResult.SUCCESS;
            }

            if (v.length() > length.max()) {
                String msg = length.msg();
                if (StringUtils.isBlank(msg)) {
                    msg = "参数验证不通过, " + paramName + "超过最大字符长度" + length.max();
                }
                return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
            } else if (v.length() < length.min()) {
                String msg = length.msg();
                if (StringUtils.isBlank(msg)) {
                    msg = "参数验证不通过, " + paramName + "低于最小字符长度" + length.min();
                }
                return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
            }
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(Length.class, this);
    }
}
