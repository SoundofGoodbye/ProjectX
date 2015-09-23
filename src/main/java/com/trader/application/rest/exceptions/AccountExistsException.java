package com.trader.application.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AccountExistsException extends RuntimeException {

	private static final long serialVersionUID = 6540987919742174436L;

	public AccountExistsException() {

	}

	public AccountExistsException(Throwable cause) {
		super(cause);
	}

}
