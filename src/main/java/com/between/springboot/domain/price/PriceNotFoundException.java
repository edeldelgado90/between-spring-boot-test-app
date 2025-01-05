package com.between.springboot.domain.price;

public class PriceNotFoundException extends RuntimeException {
  public PriceNotFoundException(String message) {
    super(message);
  }
}
