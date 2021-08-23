package com.user.center.exception;

public class SystemException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    private String message;

    private String detail;

    public SystemException(String message, String detail) {
        this.message = message;
        this.detail = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
