package com.zafatar.reservation.api.v1.exceptions;

/**
 * Custom Reservation Conflict on Table not found exception class. 
 * 
 * @author zafatar
 *
 */
public final class ReservationConflictException extends Exception {
	private static final long serialVersionUID = 9L;

	public int tableId;
	
	public ReservationConflictException(int id) {
		super("conflict with other reservation on the table #" + id + ".");
		this.tableId = id;
	}
}
