package com.trader.application.core.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = -9203565136360790306L;

	public AccountDoesNotExistException() {

	}

	public AccountDoesNotExistException(Throwable cause) {
		super(cause);
	}
}
