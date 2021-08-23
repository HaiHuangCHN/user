package com.user.center.exception;

public class BusinessException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private String detail;

    public BusinessException(String code, String message, String detail) {
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

    /**
     * TODO Can Lombok deal with such: super and child class has a field with the same name
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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
