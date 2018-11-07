package com.summer.boot.validator.config;


import com.summer.boot.validator.PropertyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class SignConfig implements InitializingBean {

    private String appId="00000";
    private String secret="";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public void afterPropertiesSet()  {
        String appId=new  PropertyUtil().getValue("sign.appId");
        if(!StringUtils.isBlank(appId)){
            this.setAppId(appId);
        }
        String secret=new  PropertyUtil().getValue("sign.secret");
        if(!StringUtils.isBlank(secret)){
            this.setSecret(secret);
        }
    }
}
