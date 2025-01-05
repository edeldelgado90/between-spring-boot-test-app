package com.between.springboot.adapter.in.rest;

import com.between.springboot.application.PriceService;
import com.between.springboot.domain.Price;
import com.between.springboot.port.in.rest.RestPricePort;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/prices")
public class PriceController implements RestPricePort {

  private final PriceService priceService;

  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  @PostMapping
  @Override
  public Mono<Price> create(@RequestBody Price price) {
    return priceService.create(price);
  }

  @DeleteMapping("/{id}")
  @Override
  public Mono<Void> delete(@PathVariable Long id) {
    return priceService.delete(id);
  }

  @GetMapping("/current")
  @ResponseBody
  @Override
  public Mono<Price> getCurrentPrice(
      @RequestParam(name = "product_id") Long productId,
      @RequestParam(name = "brand_id") Long brandId,
      @RequestParam LocalDateTime date) {
    return priceService.getCurrentPriceByProductAndBrand(productId, brandId, date);
  }

  @GetMapping("/")
  @Override
  public Mono<Page<Price>> findAll(@PageableDefault() Pageable pageable) {
    return priceService.findAllBy(pageable);
  }
}
