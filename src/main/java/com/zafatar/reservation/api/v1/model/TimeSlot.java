package com.zafatar.reservation.api.v1.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zafatar.reservation.api.v1.exceptions.InvalidTimeFormatException;

public class TimeSlot implements Serializable {
	private static final long serialVersionUID = 11L;
	private static final Logger log = LoggerFactory.getLogger(TimeSlot.class);
	
	private final String timeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

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
	 * @throws InvalidTimeFormatException 
	 */
	public boolean isValid() throws InvalidTimeFormatException {
		// 1. Check date formats.
		if ( !this.isDateTimeValid(this.getFrom()) ) {
			throw new InvalidTimeFormatException("Not Valid Time:" + this.getFrom());
		} 
		
		if ( !this.isDateTimeValid(this.getTo()) ) {
			throw new InvalidTimeFormatException("Not Valid Time:" + this.getTo());
		} 
		
		// 2. Check if from > to.
		int compare = this.getFrom().compareTo(this.getTo());
		if (compare > 0) {
			throw new InvalidTimeFormatException("From: " + this.getFrom() + " should be older than To: " + this.getTo() );
		}
		
		return true;
	}
	
    private boolean isDateTimeValid(String time) { 	
		DateFormat sdf = new SimpleDateFormat(this.timeFormat);
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(time);
			log.debug("Date Time parsing completed for " + date.toString());
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		
		return true;
    }
}
