package com.between.springboot.port.in.rest;

import com.between.springboot.domain.price.Price;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface RestPricePort extends RestPort<Price> {
  Mono<Price> getCurrentPrice(Long productId, Long brandId, LocalDateTime date);

  Mono<Page<Price>> findAll(Pageable pageable);
}
