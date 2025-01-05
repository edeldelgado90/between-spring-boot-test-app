package com.between.springboot.domain.price;

import java.util.Comparator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * PriceCalculator class.
 *
 * <p>This class is responsible for calculating the price of a product. It receives a Flux of prices
 * and returns the price with the highest priority. If there are no prices, it returns an empty
 * Mono.
 *
 * <p>This class is used by the PriceService class.
 */
public class PriceCalculator {

  /**
   * Calculate the current price of a product.
   *
   * @param prices Flux of prices.
   * @return Mono with the current price.
   */
  public static Mono<Price> calculateCurrentPrice(Flux<Price> prices) {
    return prices
        .collectList()
        .flatMap(
            p ->
                p.stream()
                    .max(Comparator.comparingInt(Price::getPriority))
                    .map(Mono::just)
                    .orElse(Mono.empty()));
  }
}
