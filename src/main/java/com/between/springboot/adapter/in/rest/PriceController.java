package com.between.springboot.adapter.in.rest;

import com.between.springboot.application.PriceService;
import com.between.springboot.domain.Price;
import com.between.springboot.port.in.rest.RestPricePort;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PriceController implements RestPricePort {

  private final PriceService priceService;

  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  @Override
  public Mono<Price> getCurrentPrice(Long productId, Long brandId, LocalDateTime date) {
    return priceService.getCurrentPriceByProductAndBrand(productId, brandId, date);
  }

  @Override
  public Mono<Price> create(Price price) {
    return priceService.create(price);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return priceService.delete(id);
  }
}
