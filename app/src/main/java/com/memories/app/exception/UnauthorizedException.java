package com.memories.app.exception;

public class UnauthorizedException extends BusinessException{
	
	private static final long serialVersionUID = 1L;

	public UnauthorizedException() {
		super();
	}

	public UnauthorizedException(String defaultMessage, String key, Object[] args) {
		super(defaultMessage, key, args);
	}

	public UnauthorizedException(String defaultMessage, Throwable cause, String key, Object[] args) {
		super(defaultMessage, cause, key, args);
	}

	public UnauthorizedException(Throwable cause, String key, Object[] args) {
		super(cause, key, args);
	}
	
	

}
