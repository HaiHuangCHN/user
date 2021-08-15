package com.user.costant;

/**
 * DB Related Error
 *
 * @author hai.huang.a@outlook.com
 * @date 2021-08-03 16:35:14
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
