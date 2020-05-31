package com.story.integration.response.model;

import java.io.Serializable;

public class Error implements Serializable {
	private static final long serialVersionUID = 56196438221373345L;
	
	String code;
	String message;

	public Error() {

	}

	public Error(String code) {

	}

	public Error(String code, String message) {
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
