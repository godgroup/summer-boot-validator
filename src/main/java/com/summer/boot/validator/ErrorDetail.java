package com.summer.boot.validator;

/**
 * 校验结果-错误详情
 */
public class ErrorDetail {
    private String fieldName;
    private Object fieldValue;
    private String reason;

    public ErrorDetail(String fieldName,Object fieldValue,String reason){
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
        this.reason=reason;
    }
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
