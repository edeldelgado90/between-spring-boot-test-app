package com.between.springboot.application;

import com.between.springboot.domain.Price;
import com.between.springboot.domain.PriceCalculator;
import com.between.springboot.port.out.DatabasePricePort;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PriceService {
  private final DatabasePricePort priceRepository;

  public PriceService(DatabasePricePort priceRepository) {
    this.priceRepository = priceRepository;
  }

  public Mono<Price> create(Price price) {
    return priceRepository.save(price);
  }

  public Mono<Void> delete(Long id) {
    return priceRepository.delete(id);
  }

  public Mono<Price> getCurrentPriceByProductAndBrand(
      Long productId, Long brandId, LocalDateTime date) {
    Flux<Price> prices = priceRepository.getCurrentPriceByProductAndBrand(productId, brandId, date);
    return PriceCalculator.calculateCurrentPrice(prices);
  }
}
