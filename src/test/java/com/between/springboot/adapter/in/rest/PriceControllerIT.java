package com.between.springboot.adapter.in.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.between.springboot.domain.price.Price;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerIT {

  @Autowired private WebTestClient webTestClient;
  private final Long productId = 35455L;
  private final Long brandId = 1L;

  @Test
  @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
  public void getCurrentPriceAt_2020_06_14_10_00_ForProduct_35455_And_Zara_Success() {
    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/prices/current")
                    .queryParam("product_id", productId)
                    .queryParam("brand_id", brandId)
                    .queryParam("date", "2020-06-14T10:00:00")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Price.class)
        .value(
            price -> assertThat(price.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(35.50)));
  }

  @Test
  @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
  public void getCurrentPriceAt_2020_06_14_16_00_ForProduct_35455_And_Zara_Success() {
    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/prices/current")
                    .queryParam("product_id", productId)
                    .queryParam("brand_id", brandId)
                    .queryParam("date", "2020-06-14T16:00:00")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Price.class)
        .value(
            price -> assertThat(price.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(25.45)));
  }

  @Test
  @DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
  public void getCurrentPriceAt_2020_06_14_21_00_ForProduct_35455_And_Zara_Success() {
    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/prices/current")
                    .queryParam("product_id", productId)
                    .queryParam("brand_id", brandId)
                    .queryParam("date", "2020-06-14T21:00:00")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Price.class)
        .value(
            price -> assertThat(price.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(35.50)));
  }

  @Test
  @DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
  public void getCurrentPriceAt_2020_06_15_10_00_ForProduct_35455_And_Zara_Success() {
    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/prices/current")
                    .queryParam("product_id", productId)
                    .queryParam("brand_id", brandId)
                    .queryParam("date", "2020-06-15T10:00:00")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Price.class)
        .value(
            price -> assertThat(price.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(30.50)));
  }

  @Test
  @DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
  public void getCurrentPriceAt_2020_06_16_21_00_ForProduct_35455_And_Zara_Success() {
    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/prices/current")
                    .queryParam("product_id", productId)
                    .queryParam("brand_id", brandId)
                    .queryParam("date", "2020-06-16T21:00:00")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Price.class)
        .value(
            price -> assertThat(price.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(38.95)));
  }
}
