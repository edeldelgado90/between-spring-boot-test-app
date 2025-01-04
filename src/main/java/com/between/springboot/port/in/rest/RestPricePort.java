package com.between.springboot.port.in.rest;

import com.between.springboot.domain.Price;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;

public interface RestPricePort extends RestPort<Price> {
  Mono<Price> getCurrentPrice(Long productId, Long brandId, LocalDateTime date);
}
