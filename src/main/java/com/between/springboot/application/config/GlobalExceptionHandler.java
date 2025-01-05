package com.between.springboot.application.config;

import com.between.springboot.domain.ErrorResponse;
import com.between.springboot.domain.price.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PriceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceNotFoundException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            UUID.randomUUID().toString());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
