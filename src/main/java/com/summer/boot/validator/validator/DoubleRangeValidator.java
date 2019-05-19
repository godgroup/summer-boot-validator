package com.summer.boot.validator.validator;


import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.DoubleRange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class DoubleRangeValidator extends AbstractAnnotationValidator<DoubleRange> {

    @Override
    public ValidateResult validate(DoubleRange doubleRange, String paramName, Object paramValue) {

        if (!doubleRange.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (paramValue != null && (paramValue.getClass() == Double.class || paramValue.getClass() == Double.TYPE)) {

            StringBuilder sb = new StringBuilder();
            for (double value : doubleRange.value()) {
                if (paramValue.equals(value)) {
                    return ValidateResult.SUCCESS;
                }
                sb.append(value).append(",");
            }
            String msg = doubleRange.msg();
            String allowedValue = sb.deleteCharAt(sb.length() - 1).toString();
            if (StringUtils.isBlank(msg)) {
                msg = "参数验证不通过, " + paramName + "无效的值, 可接受的取值有" + allowedValue;
            }
            return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(DoubleRange.class, this);
    }
}
