package com.summer.boot.validator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* @description: 验证配置
* @author:      yalunwang
* @createDate:  2018/11/11 21:16
* @version:     1.0
*/
@Component
public class ValidatorConfig {
    @Value("${summer.boot.validator.errorCode:-9999}")
    private int errorCode;
    @Value("${summer.boot.validator.enable:false}")
    private boolean enable;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
