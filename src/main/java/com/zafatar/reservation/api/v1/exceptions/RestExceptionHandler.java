package com.zafatar.reservation.api.v1.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.zafatar.reservation.api.v1.model.response.ApiBasicResponse;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	// 404 - TableNotFoundException
	@ExceptionHandler({ TableNotFoundException.class })
	public ResponseEntity<ApiBasicResponse> handleTableNotFoundException(final TableNotFoundException ex, final WebRequest request) {
		final ApiBasicResponse apiError = new ApiBasicResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
		return new ResponseEntity<ApiBasicResponse>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// 409 - ReservationConflictException
	@ExceptionHandler({ ReservationConflictException.class })
	public ResponseEntity<ApiBasicResponse> handleReservationConflictException(final ReservationConflictException ex, final WebRequest request) {
		final ApiBasicResponse apiError = new ApiBasicResponse(HttpStatus.CONFLICT, ex.getLocalizedMessage());
		return new ResponseEntity<ApiBasicResponse>(apiError, new HttpHeaders(), apiError.getStatus());	
	}

	// 500
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiBasicResponse> handleAll(final Exception ex, final WebRequest request) {
        final ApiBasicResponse apiError = new ApiBasicResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
        return new ResponseEntity<ApiBasicResponse>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}