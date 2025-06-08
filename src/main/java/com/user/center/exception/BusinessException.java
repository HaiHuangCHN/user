package com.user.center.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    private String code;

    private String detail;

    public BusinessException() {
        super();
    }

    public BusinessException(String code, String message, String detail) {
        super(message);
        this.code = code;
        this.detail = detail;
    }

    public String getDetail() {
        if (this.detail == null) {
            this.detail = super.getMessage();
        }
        return detail;
    }


}
