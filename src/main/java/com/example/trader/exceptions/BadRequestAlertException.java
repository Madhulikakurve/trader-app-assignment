package com.example.trader.exceptions;

public class BadRequestAlertException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestAlertException() {
		super();
	}
	
	public BadRequestAlertException(String message) {
		super(message);
	}

}
