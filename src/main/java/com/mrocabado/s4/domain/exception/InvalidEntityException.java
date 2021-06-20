package com.mrocabado.s4.domain.exception;

public class InvalidEntityException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2796520195103330634L;

	public InvalidEntityException(String message) {
        super(message);
    }
}
