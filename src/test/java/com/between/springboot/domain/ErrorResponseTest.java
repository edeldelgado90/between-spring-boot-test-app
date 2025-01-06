package com.between.springboot.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ErrorResponseTest {

  @Test
  public void testErrorResponseCreation() {
    LocalDateTime timestamp = LocalDateTime.now();
    int status = 404;
    String error = "Not Found";
    String requestId = "abcd-1234";

    ErrorResponse errorResponse = new ErrorResponse(timestamp, status, error, requestId);

    assertThat(errorResponse).isNotNull();
    assertThat(errorResponse.getTimestamp()).isEqualTo(timestamp);
    assertThat(errorResponse.getStatus()).isEqualTo(status);
    assertThat(errorResponse.getError()).isEqualTo(error);
    assertThat(errorResponse.getRequestId()).isEqualTo(requestId);
  }
}
