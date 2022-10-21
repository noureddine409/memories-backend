package com.memories.app.exception;

public class AmbiguousElementException extends BusinessException {
	
	private static final long serialVersionUID = 1L;

	public AmbiguousElementException() {
		super();
	}

	public AmbiguousElementException(String defaultMessage, String key, Object[] args) {
		super(defaultMessage, key, args);
	}

	public AmbiguousElementException(String defaultMessage, Throwable cause, String key, Object[] args) {
		super(defaultMessage, cause, key, args);
	}

	public AmbiguousElementException(Throwable cause, String key, Object[] args) {
		super(cause, key, args);
	}
	
	

}
