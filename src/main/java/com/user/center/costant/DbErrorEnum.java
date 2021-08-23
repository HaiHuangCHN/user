package com.user.center.costant;

/**
 * DB Related Error
 */
public enum DbErrorEnum {

    INSERT_ERROR("1", "db.insert.error"),

    UPDATE_ERROR("2", "db.update.error");

    private final String code;

    private final String message;

    DbErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
