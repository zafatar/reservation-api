package com.zafatar.reservation.api.v1.exceptions;

/**
 * Custom Reservation Conflict on Table not found exception class. 
 * 
 * @author zafatar
 *
 */
public final class InvalidTimeFormatException extends Exception {
	private static final long serialVersionUID = 19L;
	
	public InvalidTimeFormatException(String message) {
		super(message);
	}
}
