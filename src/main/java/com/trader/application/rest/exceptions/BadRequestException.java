package com.trader.application.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Allows us to send various kind of responses to the client based on program
 * not behaving correctly.
 * 
 * @author CHUCHI
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 8925254288949033288L;

	public BadRequestException() {

	}

	public BadRequestException(Throwable cause) {
		super(cause);
	}

}
