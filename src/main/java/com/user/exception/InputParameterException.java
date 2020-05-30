package com.user.exception;

public class InputParameterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private String detail;

	protected InputParameterException() {

	}

	public InputParameterException(String code, String message, String detail) {
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
