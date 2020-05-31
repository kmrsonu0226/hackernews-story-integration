package com.story.integration.response.model;

import java.io.Serializable;

import org.springframework.http.HttpStatus;


public class Error implements Serializable {
	private static final long serialVersionUID = 56196438221373345L;
	
	String code;
	String message;
	HttpStatus httpStatus;

	public Error() {

	}

	public Error(String code) {
		this.code = code;
	}

	public Error(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public Error(String code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
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
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
