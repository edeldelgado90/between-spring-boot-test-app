package com.between.springboot.adapter.in.rest;

import com.between.springboot.application.PriceService;
import com.between.springboot.domain.price.Price;
import com.between.springboot.port.in.rest.RestPricePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @Operation(
      summary = "Create a new price",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Price object to be created",
              content =
                  @Content(
                      mediaType = "application/json",
                      schema =
                          @Schema(
                              implementation = Price.class,
                              example =
                                  """
                    {
                        "brandId": 1,
                        "startDate": "2023-01-01T00:00:00",
                        "endDate": "2023-12-31T23:59:59",
                        "priceList": 1,
                        "productId": 35455,
                        "priority": 1,
                        "price": 100.00,
                        "curr": "EUR"
                    }
            """))))
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
  @Operation(
      summary = "Delete a price by Id",
      parameters = {
        @Parameter(
            name = "id",
            required = true,
            description = "ID of the price to delete",
            in = ParameterIn.PATH)
      })
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
  @Operation(
      summary = "Get current price for a product and brand",
      parameters = {
        @Parameter(
            name = "product_id",
            required = true,
            description = "ID of the product",
            in = ParameterIn.QUERY),
        @Parameter(
            name = "brand_id",
            required = true,
            description = "ID of the brand",
            in = ParameterIn.QUERY),
        @Parameter(
            name = "date",
            required = true,
            description = "Date to check current price",
            in = ParameterIn.QUERY)
      })
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
  @Operation(
      summary = "Retrieve all prices",
      parameters = {
        @Parameter(
            name = "page",
            description = "Page number to retrieve, starting from 0",
            in = ParameterIn.QUERY,
            example = "0"),
        @Parameter(
            name = "size",
            description = "Number of items to retrieve per page",
            in = ParameterIn.QUERY,
            example = "10"),
        @Parameter(
            name = "sort",
            description =
                "Sorting criteria in the format: property,(asc|desc). Default sorting order is ascending. Multiple sort criteria are supported.",
            in = ParameterIn.QUERY,
            example = "startDate,desc")
      })
  @ApiResponses(
      value = {@ApiResponse(responseCode = "200", description = "Prices retrieved successfully")})
  @Override
  public Mono<Page<Price>> findAll(@PageableDefault() Pageable pageable) {
    return priceService.findAllBy(pageable);
  }
}
