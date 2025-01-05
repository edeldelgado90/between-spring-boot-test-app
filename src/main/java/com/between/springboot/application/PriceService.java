package com.between.springboot.application;

import com.between.springboot.domain.price.Price;
import com.between.springboot.domain.price.PriceCalculator;
import com.between.springboot.domain.price.PriceNotFoundException;
import com.between.springboot.port.out.DatabasePricePort;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Mono<Page<Price>> findAllBy(Pageable pageable) {
    return priceRepository.findAllBy(pageable);
  }

  public Mono<Price> getCurrentPriceByProductAndBrand(
          Long productId, Long brandId, LocalDateTime date) {
    Flux<Price> prices = priceRepository.getCurrentPriceByProductAndBrand(productId, brandId, date);
    return PriceCalculator.calculateCurrentPrice(prices)
        .switchIfEmpty(
            Mono.error(
                new PriceNotFoundException("No price found for the given product and brand.")));
  }
}
