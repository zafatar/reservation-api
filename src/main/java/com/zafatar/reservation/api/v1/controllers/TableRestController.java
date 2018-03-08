package com.zafatar.reservation.api.v1.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zafatar.reservation.api.v1.exceptions.InvalidTimeFormatException;
import com.zafatar.reservation.api.v1.exceptions.ReservationConflictException;
import com.zafatar.reservation.api.v1.exceptions.TableNotFoundException;
import com.zafatar.reservation.api.v1.model.Reservation;
import com.zafatar.reservation.api.v1.model.Table;
import com.zafatar.reservation.api.v1.model.response.ApiResponse;
import com.zafatar.reservation.api.v1.repository.TableRepository;

@RestController
@RequestMapping("/api/v1/table")
public class TableRestController {
	private static final Logger log = LoggerFactory.getLogger(TableRestController.class);
	private final TableRepository tableRepository;

	@Autowired
	TableRestController(TableRepository tableRepository) {
		this.tableRepository = tableRepository;
	}

	/**
	 * This method create a Table in the system by reading the request body.
	 * 
	 * @param Table
	 *            a table created from request body.
	 * @return response containing the created Table.
	 */
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<ApiResponse> createTable(@RequestBody Table table) {
		this.tableRepository.save(table);

		ApiResponse ar = new ApiResponse(HttpStatus.CREATED, "Table created", table);
		return new ResponseEntity<ApiResponse>(ar, ar.getStatus());
	}

	/**
	 * This method is for the endpoint which adds reservation to the table
	 * 
	 * @param id
	 *            of the table where the reservation to be added
	 * @param reservation
	 *            to be added to the table
	 * @return response
	 * @throws TableNotFoundException
	 *             404 for the tables
	 * @throws ReservationConflictException
	 * @throws InvalidTimeFormatException 
	 * 			
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{id}/reservation")
	ResponseEntity<ApiResponse> addReservationToTable(@PathVariable int id, @RequestBody Reservation reservation)
			throws TableNotFoundException, ReservationConflictException, InvalidTimeFormatException {
		
		if(!reservation.getTimeSlot().isValid()) {
			log.debug("Exception: reservation time slot is not valid.");
		}
		
		// Find the table by id and then add the reservation.
		Table table = this.tableRepository.findById(id);
		table.addReservation(reservation);
		this.tableRepository.update(table);
		
		ApiResponse ar = new ApiResponse(HttpStatus.OK, "Reservation added", table);
		return new ResponseEntity<ApiResponse>(ar, ar.getStatus());
	}

	/**
	 * This method returns the table requested by its id.
	 * 
	 * @param id
	 *            of the table.
	 * @return response containing the requested table.
	 * @throws TableNotFoundException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<ApiResponse> getTable(@PathVariable int id) throws TableNotFoundException {
		Table table = this.tableRepository.findById(id);

		ApiResponse ar = new ApiResponse(HttpStatus.OK, "Table returned", table);
		return new ResponseEntity<ApiResponse>(ar, ar.getStatus());
	}
}
