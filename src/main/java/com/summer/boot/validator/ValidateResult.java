package com.summer.boot.validator;

/**
 * 校验结果
 */
public class ValidateResult {

    private int code;
    private boolean valid = false;
    private ErrorDetail errorDetail;
    public static final ValidateResult SUCCESS = new ValidateResult(true);

    private ValidateResult() {

    }

    private ValidateResult(boolean valid) {
        this.valid = valid;
    }

    private ValidateResult(boolean valid, int code, ErrorDetail errorDetail) {
        this.valid = valid;
        this.code = code;
        this.errorDetail = errorDetail;
    }

    public static ValidateResult ERROR(int code, ErrorDetail errorDetail) {
        return new ValidateResult(false, code, errorDetail);
    }

    public boolean isValid() {
        return valid;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }

    public int getCode() {
        return code;
    }

}
