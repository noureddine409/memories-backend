package com.memories.app.exception;

public class UnauthorizedFileFormatException extends BusinessException{

	
	private static final long serialVersionUID = -4609767528345037662L;

	public UnauthorizedFileFormatException() {
		super();
	}

	public UnauthorizedFileFormatException(String defaultMessage, String key, Object[] args) {
		super(defaultMessage, key, args);
	}

	public UnauthorizedFileFormatException(String defaultMessage, Throwable cause, String key, Object[] args) {
		super(defaultMessage, cause, key, args);
	}

	public UnauthorizedFileFormatException(Throwable cause, String key, Object[] args) {
		super(cause, key, args);
	}
	
	

}
