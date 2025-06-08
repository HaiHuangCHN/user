package com.user.center.costant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * DB Related Error
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DbErrorEnum {

    INSERT_ERROR("1", "db.insert.error"),

    UPDATE_ERROR("2", "db.update.error");

    private final String code;

    private final String message;

}
