package org.commonjava.exception;

public class UnauthenticatedAccessException extends RuntimeException{

	public UnauthenticatedAccessException() {
		super();
	}

	public UnauthenticatedAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthenticatedAccessException(String message) {
		super(message);
	}

	public UnauthenticatedAccessException(Throwable cause) {
		super(cause);
	}

}
