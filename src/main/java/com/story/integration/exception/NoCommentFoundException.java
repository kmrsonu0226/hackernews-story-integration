package com.story.integration.exception;

public class NoCommentFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8397996321962637917L;

	public NoCommentFoundException(String message) {
		super(message);
	}

	public NoCommentFoundException() {
		super("Comment not found");
	}

}
