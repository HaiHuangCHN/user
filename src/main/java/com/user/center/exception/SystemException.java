package com.user.center.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    private String detail;

    public SystemException() {
        super();
    }

    public SystemException(String message, String detail) {
        super(message);
        this.detail = detail;
    }


}
