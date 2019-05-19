package com.summer.boot.validator.validator;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.summer.boot.validator.ErrorDetail;
import com.summer.boot.validator.ValidateResult;
import com.summer.boot.validator.annotation.Json;
import com.summer.boot.validator.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Josh on 17-6-10.
 */
@Component
public class JsonValidator extends AbstractAnnotationValidator<Json> {

    @Override
    public ValidateResult validate(Json json, String paramName, Object paramValue) {

        if (!json.enable()) {
            return ValidateResult.SUCCESS;
        }

        if (paramValue != null &&
                (paramValue instanceof String && !"".equals(paramValue))) {

            try {
                if (json.format() == JsonFormat.jsonObject) {
                    JSON.parseObject((String) paramValue);
                } else if (json.format() == JsonFormat.jsonArray) {
                    JSON.parseArray((String) paramValue);
                } else {
                    String msg = "参数验证不通过, " + paramName + "指定了无效的json格式";
                    ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName,"", msg));
                }
            } catch (JSONException exception) {
                String msg = json.msg();
                if (StringUtils.isBlank(msg)) {
                    msg = "参数验证不通过, " + paramName + "json格式错误";
                }
                return ValidateResult.ERROR(this.getErrorCode(), new ErrorDetail(paramName, paramValue, msg));
            }
        }
        return ValidateResult.SUCCESS;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerValidator(Json.class, this);
    }
}
