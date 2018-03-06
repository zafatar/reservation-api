package com.zafatar.reservation.api.v1.model;

import java.io.Serializable;

public class TimeSlot implements Serializable {
	private static final long serialVersionUID = 11L;	
	// TODO: TimeSlot object should hold Date Time objects
	//       not just bare String
	private String from;
	private String to;

	public TimeSlot(String from, String to) {
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * This method checks that current TimeSlot object conflicts with the other
	 * TimeSlot objects.
	 * 
	 * @param timeSlot TimeSlot object to be compared
	 * @return boolean flag of the conflict (by default FALSE)
	 */
	public boolean conflict(TimeSlot timeSlot2) {
		// First case  : ts1.from <= ts2.from < ts1.to => Overlap
		if ( this.getFrom().compareTo(timeSlot2.getFrom()) <= 0 && 
			 this.getTo().compareTo(timeSlot2.getFrom())   > 0 ) {
			return true;
		} 

		// Second case : ts1.from < ts2.to   <= ts1.to => Overlap 
		if ( this.getFrom().compareTo(timeSlot2.getTo()) < 0 &&
		     this.getTo().compareTo(timeSlot2.getTo())   >= 0 ) {
			return true;
		}
		
		return false;
	}

	/**
	 * This method checks the validity of the TimeSlot based on the logic of
	 * that from should be smaller than to as simple string comparison.
	 * 
	 * @return boolean flag of the validity (by default TRUE)
	 */
	public boolean isValid() {
		boolean valid = true;

		int compare = this.getFrom().compareTo(this.getTo());
		if (compare > 0) {
			valid = false;
		}

		return valid;
	}
}
