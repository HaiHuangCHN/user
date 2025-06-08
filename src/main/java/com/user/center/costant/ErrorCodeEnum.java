package com.user.center.costant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCodeEnum {
	INVALID_USER(200, "UC_001", "Invalid user", ""),
	FAIL_VERIFY_TOKEN(200, "UC_002", "Fail to verify token", ""),
	INVALID_CLAIM(200, "UC_003", "Invalid claim", ""),
	ERROR_WHEN_HOUSEKEEP(200, "UC_003", "error when housekeep", ""),
	INVALID_USERNAME(200, "UC_004", "Invalid username", "Username is already used, please try anothor"),
	INCOMPLETE_REQUEST_BODY(200, "UC_005", "Invalid", "");

    private final Integer httpStatusCode;

    private final String selfDefinedCode;

    private final String message;

    private final String detail;

}
