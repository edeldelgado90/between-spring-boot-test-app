package com.between.springboot.port.out;

import com.between.springboot.domain.price.Price;
import java.time.LocalDateTime;
import reactor.core.publisher.Flux;

public interface DatabasePricePort extends DatabasePort<Price> {
  Flux<Price> getCurrentPriceByProductAndBrand(Long productId, Long brandId, LocalDateTime date);
}
