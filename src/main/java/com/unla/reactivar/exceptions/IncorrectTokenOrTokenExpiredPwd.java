package com.unla.reactivar.exceptions;

public class IncorrectTokenOrTokenExpiredPwd extends RuntimeException {

	private static final long serialVersionUID = -9090379577606600324L;
	private String errorMessage;
	private String errorCode;
	
	public IncorrectTokenOrTokenExpiredPwd() {
		// TODO Auto-generated constructor stub
	}

	public IncorrectTokenOrTokenExpiredPwd(String errorCode, String errorMessage) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	
	public IncorrectTokenOrTokenExpiredPwd(String object) {
		super();
		this.errorMessage = object;
	}


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
}
