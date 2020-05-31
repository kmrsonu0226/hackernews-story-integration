package com.story.integration.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.story.integration.response.model.Error;
import com.story.integration.response.model.Response;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ExceptionHandler(ContentNotFoundException.class)
	public @ResponseBody Response<Error> handleContentNotFoundException(Exception e) {
		logger.error("ERROR", e);
		return new Response<Error>(new Error("ContentNotFoundException", e.getMessage()));
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ BadRequestException.class, MethodArgumentTypeMismatchException.class })
	public @ResponseBody Response<Error> handleBadRequestException(Exception e) {
		logger.error("ERROR", e);
		return new Response<Error>(new Error("BadRequestException", e.getMessage()));
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ NoCommentFoundException.class, StoryNotFoundException.class })
	public @ResponseBody Response<Error> handleNoCommentException(Exception e) {
		logger.error("ERROR", e);
		return new Response<Error>(new Error("NotFoundException", e.getMessage()));
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ Exception.class})
	public @ResponseBody Response<Error> handleException(Exception e) {
		logger.error("ERROR", e);
		return new Response<Error>(new Error("InternalServerError", e.getMessage()));
	}

}
