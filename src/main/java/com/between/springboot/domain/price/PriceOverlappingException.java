package com.between.springboot.domain.price;

public class PriceOverlappingException extends RuntimeException {
  public PriceOverlappingException(String message) {
    super(message);
  }
}
