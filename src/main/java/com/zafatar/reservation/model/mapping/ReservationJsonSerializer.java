package com.zafatar.reservation.model.mapping;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zafatar.reservation.api.v1.model.Reservation;

@JsonComponent
public class ReservationJsonSerializer extends JsonSerializer<Reservation> {
	@Override
	public void serialize(Reservation reservation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("customer_name", reservation.getCustomerName());
		jsonGenerator.writeStringField("from", reservation.getTimeSlot().getFrom());
		jsonGenerator.writeStringField("to", reservation.getTimeSlot().getTo());
		jsonGenerator.writeEndObject();
	}
}
