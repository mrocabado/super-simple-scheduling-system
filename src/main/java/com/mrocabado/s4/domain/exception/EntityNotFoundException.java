package com.mrocabado.s4.domain.exception;

public class EntityNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1566956459754478209L;

	public EntityNotFoundException(String message) {
        super(message);
    }
}
