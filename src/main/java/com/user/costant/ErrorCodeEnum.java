package com.user.costant;

/**
 * Disable setter as we want ALL the fields to be something like hard code
 */
public enum ErrorCodeEnum {
	INVALID_USER(200, "PF_001", "Invalid user", ""),
	FAIL_VERIFY_TOKEN(200, "PF_002", "Fail to verify token", ""),
	INVALID_CLAIM(200, "PF_003", "Invalid claim", ""),
	ERROR_WHEN_HOUSEKEEP(200, "PF_003", "error when housekeep", ""),
	INVALID_USERNAME(200, "PF_004", "Invalid username", "Username is already used, please try anothor"),
	INCOMPLETE_REQUEST_BODY(200, "PF_005", "Invalid", "");

    private ErrorCodeEnum(int httpStatusCode, String selfDefinedCode, String msg, String detail) {
        this.httpStatusCode = httpStatusCode;
        this.selfDefinedCode = selfDefinedCode;
        this.message = msg;
        this.detail = detail;
    }

    public Integer httpStatusCode;
    public String selfDefinedCode;
    public String message;
    public String detail;

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getSelfDefinedCode() {
        return selfDefinedCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

}
