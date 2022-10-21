package com.memories.app.exception;

public class BadRequestException extends BusinessException{

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String defaultMessage, String key, Object[] args) {
		super(defaultMessage, key, args);
	}

	public BadRequestException(String defaultMessage, Throwable cause, String key, Object[] args) {
		super(defaultMessage, cause, key, args);
	}

	public BadRequestException(Throwable cause, String key, Object[] args) {
		super(cause, key, args);
	}
	
	

}
