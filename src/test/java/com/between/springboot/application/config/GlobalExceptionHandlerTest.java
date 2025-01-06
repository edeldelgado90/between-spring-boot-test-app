package com.between.springboot.application.config;

import com.between.springboot.domain.ErrorResponse;
import com.between.springboot.domain.price.PriceNotFoundException;
import com.between.springboot.domain.price.PriceOverlappingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

  @Test
  public void handlePriceNotFoundExceptionReturns404() {
    PriceNotFoundException exception = new PriceNotFoundException("Price not found.");

    ResponseEntity<ErrorResponse> responseEntity =
        globalExceptionHandler.handlePriceNotFoundException(exception);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    ErrorResponse errorResponse = responseEntity.getBody();
    assertThat(errorResponse).isNotNull();
    assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(errorResponse.getError()).isEqualTo("Price not found.");
    assertThat(errorResponse.getTimestamp()).isBefore(LocalDateTime.now());
    assertThat(errorResponse.getRequestId()).isNotNull();
  }

  @Test
  public void handlePriceOverlappingExceptionReturns409() {
    PriceOverlappingException exception = new PriceOverlappingException("Price overlaps.");

    ResponseEntity<ErrorResponse> responseEntity =
        globalExceptionHandler.handlePriceOverlappingException(exception);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

    ErrorResponse errorResponse = responseEntity.getBody();
    assertThat(errorResponse).isNotNull();
    assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    assertThat(errorResponse.getError()).isEqualTo("Price overlaps.");
    assertThat(errorResponse.getTimestamp()).isBefore(LocalDateTime.now());
    assertThat(errorResponse.getRequestId()).isNotNull();
  }
}
