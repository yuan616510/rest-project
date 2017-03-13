package com.trizic.restapi.model;

public class ErrorResponse {
	private String errorCode;
	
	public ErrorResponse(String err){
		this.errorCode = err;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
