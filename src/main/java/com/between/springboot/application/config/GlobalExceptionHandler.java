package com.between.springboot.application.config;

import com.between.springboot.domain.ErrorResponse;
import com.between.springboot.domain.price.PriceNotFoundException;
import com.between.springboot.domain.price.PriceOverlappingException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PriceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceNotFoundException ex) {
    return getResponseEntity(HttpStatus.NOT_FOUND, "Price not found");
  }

  @ExceptionHandler(PriceOverlappingException.class)
  public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceOverlappingException ex) {
    return getResponseEntity(HttpStatus.CONFLICT, "Price is overlapping");
  }

  private static ResponseEntity<ErrorResponse> getResponseEntity(
      HttpStatus status, String message) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            LocalDateTime.now(), status.value(), message, UUID.randomUUID().toString());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
