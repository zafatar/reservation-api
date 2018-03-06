package com.zafatar.reservation.api.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * All starts here.
 * 
 * @author zafatar
 *
 */
@SpringBootApplication
public class ReservationApiApplication {
	private static final Logger log = LoggerFactory.getLogger(ReservationApiApplication.class);
	
	public static void main(String[] args) {
		log.info("ReservationApi application started.");
		SpringApplication.run(ReservationApiApplication.class, args);
	}
}
