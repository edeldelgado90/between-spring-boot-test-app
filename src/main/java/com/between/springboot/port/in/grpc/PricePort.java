package com.between.springboot.port.in.grpc;

import com.between.springboot.domain.Price;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;

public interface PricePort extends Port<Price> {
  Mono<Price> getCurrentPrice(Long productId, Long brandId, LocalDateTime date);
}
