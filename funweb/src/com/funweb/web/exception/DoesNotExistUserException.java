package com.funweb.web.exception;

public class DoesNotExistUserException extends LoginFailException {

	public DoesNotExistUserException() {
	}

	public DoesNotExistUserException(Throwable t) {
		super(t);
	}

}
