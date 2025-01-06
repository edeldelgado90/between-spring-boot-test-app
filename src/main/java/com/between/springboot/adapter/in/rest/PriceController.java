package com.between.springboot.adapter.in.rest;

import com.between.springboot.application.PriceService;
import com.between.springboot.domain.price.Price;
import com.between.springboot.port.in.rest.RestPricePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(summary = "Create a new price")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Price created successfully"),
        @ApiResponse(responseCode = "409", description = "Price is overlapping with another price"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
      })
  @Override
  public Mono<Price> create(@RequestBody Price price) {
    return priceService.create(price);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a price by Id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Price deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Price not found")
      })
  @Override
  public Mono<Void> delete(@PathVariable Long id) {
    return priceService.delete(id);
  }

  @GetMapping("/current")
  @Operation(summary = "Get current price for a product and brand")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Current price found"),
        @ApiResponse(responseCode = "404", description = "Price not found for the given criteria")
      })
  @ResponseBody
  @Override
  public Mono<Price> getCurrentPrice(
      @RequestParam(name = "product_id") Long productId,
      @RequestParam(name = "brand_id") Long brandId,
      @RequestParam LocalDateTime date) {
    return priceService.getCurrentPriceByProductAndBrand(productId, brandId, date);
  }

  @GetMapping("/")
  @Operation(summary = "Retrieve all prices")
  @ApiResponses(
      value = {@ApiResponse(responseCode = "200", description = "Prices retrieved successfully")})
  @Override
  public Mono<Page<Price>> findAll(@PageableDefault() Pageable pageable) {
    return priceService.findAllBy(pageable);
  }
}
