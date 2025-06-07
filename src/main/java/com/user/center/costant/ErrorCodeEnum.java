package com.user.center.costant;

/**
 * Disable setter as we want ALL the fields to be something like hard code
 *
 * TODO Move to Exception?
 */
public enum ErrorCodeEnum {
	INVALID_USER(200, "UC_001", "Invalid user", ""),
	FAIL_VERIFY_TOKEN(200, "UC_002", "Fail to verify token", ""),
	INVALID_CLAIM(200, "UC_003", "Invalid claim", ""),
	ERROR_WHEN_HOUSEKEEP(200, "UC_003", "error when housekeep", ""),
	INVALID_USERNAME(200, "UC_004", "Invalid username", "Username is already used, please try anothor"),
	INCOMPLETE_REQUEST_BODY(200, "UC_005", "Invalid", "");

    ErrorCodeEnum(int httpStatusCode, String selfDefinedCode, String msg, String detail) {
        this.httpStatusCode = httpStatusCode;
        this.selfDefinedCode = selfDefinedCode;
        this.message = msg;
        this.detail = detail;
    }

    private final Integer httpStatusCode;
    private final String selfDefinedCode;
    private final String message;
    private final String detail;

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
