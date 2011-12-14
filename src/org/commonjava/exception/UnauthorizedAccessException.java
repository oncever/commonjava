package org.commonjava.exception;

public class UnauthorizedAccessException extends RuntimeException{

	public UnauthorizedAccessException() {
		super();
	}

	public UnauthorizedAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedAccessException(String message) {
		super(message);
	}

	public UnauthorizedAccessException(Throwable cause) {
		super(cause);
	}

}
