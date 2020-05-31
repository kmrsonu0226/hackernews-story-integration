package com.story.integration.exception;

public class BadRequestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8801310509978484099L;

	public BadRequestException(String message) {
		super(message);
	}
	
	public BadRequestException() {
		super("Invalid request");
	}
}
