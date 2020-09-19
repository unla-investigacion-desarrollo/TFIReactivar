package com.unla.reactivar.exceptions;

public class UserIsAlreadyInactive extends RuntimeException {

	private static final long serialVersionUID = -9090379577606600324L;
	private String errorMessage;
	private String errorCode;
	
	public UserIsAlreadyInactive() {
		// TODO Auto-generated constructor stub
	}

	public UserIsAlreadyInactive(String errorCode, String errorMessage) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	
	public UserIsAlreadyInactive(String object) {
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
