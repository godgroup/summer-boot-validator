package com.summer.boot.validator;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    public  String getValue(String key) {
        try {
            InputStream is ;//= new ClassPathResource("application.properties").getInputStream();
            ClassLoader CL = this.getClass().getClassLoader();

            if (CL != null) {
                is = CL.getResourceAsStream("classpath*:/application.properties");
            }else {
                is = ClassLoader.getSystemResourceAsStream("application.properties");
            }
            Properties properties = new Properties();
            properties.load(is);
            return properties.getProperty(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static void main(String[] args) {
        String value = new PropertyUtil().getValue("sign.test");
        System.out.println(value);
    }
}
