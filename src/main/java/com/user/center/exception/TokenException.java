package com.user.center.exception;

@Deprecated
public class TokenException extends Exception {


    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private String detail;

    public TokenException() {
    }

    public TokenException(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessag(String message) {
        this.message = message;
    }

    public String getDetail() {
        if (this.detail == null) {
            this.detail = this.message;
        }
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
