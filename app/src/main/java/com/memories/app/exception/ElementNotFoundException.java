package com.memories.app.exception;

public class ElementNotFoundException extends BusinessException {
	
	private static final long serialVersionUID = 3514588905105588434L;

	public ElementNotFoundException() {
		super();
	}

	public ElementNotFoundException(String defaultMessage, String key, Object[] args) {
		super(defaultMessage, key, args);
	}

	public ElementNotFoundException(String defaultMessage, Throwable cause, String key, Object[] args) {
		super(defaultMessage, cause, key, args);
	}

	public ElementNotFoundException(Throwable cause, String key, Object[] args) {
		super(cause, key, args);
	}

}
