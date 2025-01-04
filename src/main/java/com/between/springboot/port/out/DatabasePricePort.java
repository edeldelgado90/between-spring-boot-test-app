package com.between.springboot.port.out;

import com.between.springboot.domain.Price;
import java.time.LocalDateTime;
import reactor.core.publisher.Flux;

public interface DatabasePricePort extends DatabasePort<Price> {
  Flux<Price> getPrices(Long productId, Long brandId, LocalDateTime date);
}
