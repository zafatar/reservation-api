package com.zafatar.reservation.api.v1.exceptions;

/**
 * Custom Table not found exception class. 
 * 
 * @author zafatar
 *
 */
public final class TableNotFoundException extends Exception {
	private static final long serialVersionUID = 8L;

	public int tableId;
	
	public TableNotFoundException(int id) {
		super("could not find table with '" + id + "'.");
		this.tableId = id;
	}
}
