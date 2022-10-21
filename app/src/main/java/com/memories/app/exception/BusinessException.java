package com.memories.app.exception;



public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String key;
	private Object[] args;
	public BusinessException() {
		super();
	}
	public BusinessException(String defaultMessage, String key, Object[] args) {
		super(defaultMessage);
		this.key = key;
		this.args = args;
	}
	
	public BusinessException(String defaultMessage, Throwable cause, String key, Object[] args) {
		super(defaultMessage, cause);
		this.key = key;
		this.args = args;
	}
	
	public BusinessException(final Throwable cause, final String key, final Object[] args) {
        super(cause);
        this.key = key;
        this.args = args;
    }
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getKey() {
		return key;
	}
	public Object[] getArgs() {
		return args;
	}
	
	
}
