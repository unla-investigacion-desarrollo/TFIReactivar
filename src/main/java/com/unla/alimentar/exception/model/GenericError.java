package com.unla.alimentar.exception.model;

public class GenericError {

	private String code = null;
	private String message = null;

	public GenericError() {
		super();
	}

	public GenericError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
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

	public void setMessage(String message) {
		this.message = message;
	}

}