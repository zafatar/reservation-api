package com.zafatar.reservation.api.v1.model.mapping;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zafatar.reservation.api.v1.model.Reservation;

public class ReservationJsonSerializerTest {
	@Test
	public void givenFieldNameIsChanged_whenSerializing_thenCorrect() 
			throws JsonParseException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		Reservation reservation = new Reservation("Zuko", "2018-01-06T18:00:00.000Z", "2018-01-06T18:30:00.000Z");		
		String reservationAsString = mapper.writeValueAsString(reservation);
		
		assertThat(reservationAsString, not(containsString("customerName")));
	    assertThat(reservationAsString, containsString("customer_name"));
	}
}
