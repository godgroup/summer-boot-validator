package com.summer.boot.validator.exception;


import com.summer.boot.validator.ErrorDetail;

/**
 * Created by Josh on 17-11-9.
 */
public class ValidateRuntimeException extends RuntimeException {

    private int code;
    private ErrorDetail failedReason;

    public ValidateRuntimeException() {

    }

    public ValidateRuntimeException(int code, ErrorDetail failedReason) {
        this.code = code;
        this.failedReason = failedReason;
    }

    public int getCode() {
        return code;
    }

    public ErrorDetail getFailedReason() {
        return failedReason;
    }

    public String getMessage() {
        return failedReason == null ? "" : failedReason.toString();
    }
}
