package com.memories.app.exception;

public class ElementAlreadyExistException extends BusinessException {

	private static final long serialVersionUID = 712719389442641868L;

	public ElementAlreadyExistException() {
		super();
	}

	public ElementAlreadyExistException(String defaultMessage, String key, Object[] args) {
		super(defaultMessage, key, args);
	}

	public ElementAlreadyExistException(String defaultMessage, Throwable cause, String key, Object[] args) {
		super(defaultMessage, cause, key, args);
	}

	public ElementAlreadyExistException(Throwable cause, String key, Object[] args) {
		super(cause, key, args);
	}

}
