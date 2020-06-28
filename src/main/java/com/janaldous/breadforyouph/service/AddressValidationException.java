package com.janaldous.breadforyouph.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AddressValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
