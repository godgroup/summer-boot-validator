package com.summer.boot.validator.validator;


import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.StringRange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * String 数组存放可使用的参数值
 */
@Component
public class StringRangeValidator extends AbstractAnnotationValidator<StringRange> {

    @Override
    public ValidateResult validate(StringRange in, String paramName, Object paramValue) {

        if (!in.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (paramValue != null &&
                (paramValue instanceof String && !"".equals(paramValue))) {
            StringBuilder sb = new StringBuilder();
            for (String value : in.value()) {
                if (value.equals(paramValue)) {
                    return ValidateResult.SUCCESS;
                }
                sb.append(value).append(",");
            }
            String msg = in.msg();
            String allowValue = sb.deleteCharAt(sb.length() - 1).toString();
            if (StringUtils.isBlank(msg)) {
                msg = "参数验证不通过, " + paramName + "无效的值, 可接受的取值有" + allowValue;
            }
            return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(StringRange.class, this);
    }
}
