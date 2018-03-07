package com.zafatar.reservation.api.v1.model.mapping;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.zafatar.reservation.api.v1.model.Reservation;
import com.zafatar.reservation.api.v1.model.TimeSlot;

/**
 * This is the custom deserialization class for Reservation JSON.
 * @author zafatar
 *
 */
public class ReservationJsonDeserializer extends JsonDeserializer<Reservation> {
	
	 @Override
	    public Reservation deserialize(JsonParser jsonParser, 
	      DeserializationContext deserializationContext) throws IOException, 
	      JsonProcessingException {
		 TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
		 TextNode customerName = (TextNode) treeNode.get("customer_name");
		 
		 TreeNode timeSlotNode = (TreeNode) treeNode.get("timeslot");
		 TextNode from = (TextNode) timeSlotNode.get("from");
		 TextNode to   = (TextNode) timeSlotNode.get("to");
		 
		 TimeSlot timeSlot = new TimeSlot(from.asText(), to.asText());
		 
		 return new Reservation(customerName.asText(), timeSlot);
	 }
}