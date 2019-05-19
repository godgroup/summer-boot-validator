package com.summer.boot.validator.validator;

import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.IntegerRange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Josh on 17-7-18.
 */
@Component
public class IntegerRangeValidator extends AbstractAnnotationValidator<IntegerRange> {

    @Override
    public ValidateResult validate(IntegerRange in, String paramName, Object paramValue) {

        if (!in.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (paramValue != null && (paramValue.getClass() == Integer.class || paramValue.getClass() == Integer.TYPE)) {

            StringBuilder sb = new StringBuilder();
            for (int value : in.value()) {
                if (paramValue.equals(value)) {
                    return ValidateResult.SUCCESS;
                }
                sb.append(value).append(",");
            }
            String msg = in.msg();
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
        registerValidator(IntegerRange.class, this);
    }
}
