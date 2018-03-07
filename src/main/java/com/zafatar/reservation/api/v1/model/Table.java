package com.zafatar.reservation.api.v1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zafatar.reservation.api.v1.exceptions.ReservationConflictException;

public class Table implements Serializable {
	private static final long serialVersionUID = 42L;
	
	private int			  	  id;
	private String			  name;
	private List<Reservation> reservations;
	
	public Table() {
		this.reservations = new ArrayList<Reservation>();
	}
	
	public Table(String name) {
		this.name = name;
	}
	
	public void addReservation(Reservation reservation) throws ReservationConflictException {
		// Check the availability.
		if (!this.isTableAvailable(reservation)) {
			throw new ReservationConflictException(this.getId());
		}
		
		this.getReservations().add(reservation);
	}

	/**
	 * This method controls if the given reservations makes a conflict with the existing reservations on the table.
	 * 
	 * @param reservation to be compared against the existing reservations.
	 * @return conflict flag as boolean
	 */
	private boolean isTableAvailable(Reservation reservation) {
		boolean available = true;
		
		if (this.reservations.size() > 0) { 
			for(Reservation tableReservation: this.reservations) {
				if (tableReservation.conflict(reservation)) {
					available = false;
					break;
				}		
			}
		}
		
		return available;
	}
	
	/**
	 * Getters and setters.
	 */
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Reservation> getReservations() {		
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}
