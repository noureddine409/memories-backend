package com.memories.app.exception;

public class ElementNotUniqueException extends BusinessException{

	private static final long serialVersionUID = -1146463968514572658L;

	public ElementNotUniqueException() {
		super();
	}

	public ElementNotUniqueException(String defaultMessage, String key, Object[] args) {
		super(defaultMessage, key, args);
	}

	public ElementNotUniqueException(String defaultMessage, Throwable cause, String key, Object[] args) {
		super(defaultMessage, cause, key, args);
	}

	public ElementNotUniqueException(Throwable cause, String key, Object[] args) {
		super(cause, key, args);
	}

}
