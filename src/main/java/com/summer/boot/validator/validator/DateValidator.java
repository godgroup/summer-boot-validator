package com.summer.boot.validator.validator;


import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;



/**
 * String 类型的字段验证
 */
@Component
public class DateValidator extends AbstractAnnotationValidator<Date> {

    @Override
    public ValidateResult validate(Date date, String paramName, Object paramValue) {
        if (!date.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (paramValue != null && paramValue instanceof String) {

            String value = (String) paramValue;

            if (!"".equals(value)) {
                try {
                    DateUtils.parseDateStrictly(value, date.format());
                } catch (ParseException e) {
                    String msg = date.msg();
                    if (StringUtils.isBlank(msg)) {
                        msg = "参数验证不通过, " + paramName + "日期格式错误, 需要" + date.format() + "的日期格式";
                    }
                    return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
                }
            }
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(Date.class, this);
    }
}
