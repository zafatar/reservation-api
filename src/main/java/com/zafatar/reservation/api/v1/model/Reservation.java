package com.zafatar.reservation.api.v1.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zafatar.reservation.api.v1.model.mapping.ReservationJsonSerializer;

@JsonSerialize(using = ReservationJsonSerializer.class)
// - This JsonDeserialize is commented because we don't need this for now.
// @JsonProperty annotation and Serializable works just fine.
// @JsonDeserialize(using = ReservationJsonDeserializer.class)
public class Reservation implements Serializable {
	private static final long serialVersionUID = 66L;

	private String customerName;
	private TimeSlot timeSlot;

	public Reservation() {
	}

	public Reservation(String customerName, TimeSlot timeSlot) {
		this.customerName = customerName;
		this.timeSlot = timeSlot;
	}

	/**
	 * Getters and setters.
	 */
	@JsonProperty("customer_name")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@JsonProperty("timeslot")
	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	/**
	 * This method controls if the current reservation object conflicts with the
	 * other reservation given as parameter.
	 * 
	 * @param reservation to be compared.
	 * @return boolean flag of the conflict (by default FALSE)
	 */
	public boolean conflict(Reservation reservation) {
		if (this.getTimeSlot().conflict(reservation.getTimeSlot())) {
			return true;
		}

		return false;
	}
}
