package com.story.integration.exception;

public class ContentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9220311925811168007L;

	public ContentNotFoundException(String message) {
		super(message);
	}

	public ContentNotFoundException() {
		super("Content not found");
	}

}
