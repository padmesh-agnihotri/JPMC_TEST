package com.jpmc.test.tradeprocess.validator;

/*
 * Exception when TradeInstruction is not valid
 */
public class ValidationException extends Exception {
	private static final long serialVersionUID = 1L;

	public ValidationException(String errorMessage) {
		super(errorMessage);
	}

}
