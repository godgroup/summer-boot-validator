package com.summer.boot.validator.validator;


import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.Decimal;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DecimalValidator extends AbstractAnnotationValidator<Decimal> {

    @Override
    public ValidateResult validate(Decimal decimal, String paramName, Object paramValue) {

        if (!decimal.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (paramValue != null) {

            BigDecimal value = null;
            Class<?> clazz = paramValue.getClass();
            if ((clazz == Float.TYPE || clazz == Float.class) || (clazz == Double.TYPE || clazz == Double.class)) {
                value = new BigDecimal(paramValue.toString());
            }
            if (clazz == BigDecimal.class) {
                value = (BigDecimal) paramValue;
            }
            if (value != null) {
                if (decimal.numLength() > 0) {
                    String s = paramValue.toString();
                    int lastIndexOfDot = s.lastIndexOf(".");
                    int lastIndexOfE = s.lastIndexOf("E");
                    int numLength = s.length();
                    if (lastIndexOfDot > 0) {
                        numLength = s.substring(0, lastIndexOfDot).length();
                    } else if (lastIndexOfE > 0) {
                        int leftLength = s.substring(0, lastIndexOfE).length();
                        int rightValue = Integer.valueOf(s.substring(lastIndexOfE + 1));
                        numLength = (leftLength + rightValue) <= 1 ? 1 : (leftLength + rightValue);
                    }
                    if (decimal.numLength() > 0 && numLength > decimal.numLength()) {
                        String msg = "参数验证不通过, " + paramName + "最多保留" + decimal.numLength() + "位整数";
                        return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
                    }
                }

                // 如果值为0, 则不做小数精度验证
                if (value.compareTo(BigDecimal.ZERO) != 0 && value.scale() > decimal.scale()) {
                    String msg = "参数验证不通过, " + paramName + "最多保留" + decimal.scale() + "位小数";
                    return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
                }
                if (value.doubleValue() > decimal.max()) {
                    String msg = "参数验证不通过," + paramName + "大于最大值" + decimal.max();
                    return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
                }
                if (value.doubleValue() < decimal.min()) {
                    String msg = "参数验证不通过," + paramName + "小于最小值" + decimal.min();
                    return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
                }
                return ValidateResult.SUCCESS;
            }
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(Decimal.class, this);
    }
}
