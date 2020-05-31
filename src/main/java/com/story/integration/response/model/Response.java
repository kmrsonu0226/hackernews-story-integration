package com.story.integration.response.model;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {
	private static final long serialVersionUID = 56196438221373507L;

	@JsonIgnore
	protected HttpStatus httpStatus;

	public Response() {
		this.timestamp = System.currentTimeMillis();
	}

	public Response(T t) {
		if (t instanceof Error) {
			this.error = (Error) t;
		} else {
			this.payload = t;
		}

		this.timestamp = System.currentTimeMillis();
	}

	private T payload;

	private Error error;

	protected long timestamp;

	/*
	 * public Response(Error e) { this.error = e; this.timestamp =
	 * System.currentTimeMillis(); }
	 */

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

}
