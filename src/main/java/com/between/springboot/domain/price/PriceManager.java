package com.between.springboot.domain.price;

import java.util.Comparator;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Builder
public class PriceManager {

  public Mono<Price> findHighestPriorityPrice(Flux<Price> prices) {
    return prices
        .collectList()
        .flatMap(
            p ->
                p.stream()
                    .max(Comparator.comparingInt(Price::getPriority))
                    .map(Mono::just)
                    .orElse(Mono.empty()));
  }

  public Mono<Boolean> doesPriceOverlap(Flux<Price> existingPrices, Price newPrice) {
    return existingPrices.any(existingPrice -> isOverlapping(existingPrice, newPrice));
  }

  private static boolean isOverlapping(Price existingPrice, Price newPrice) {
    return (newPrice.getStartDate().isBefore(existingPrice.getEndDate())
        && newPrice.getEndDate().isAfter(existingPrice.getStartDate()));
  }
}
