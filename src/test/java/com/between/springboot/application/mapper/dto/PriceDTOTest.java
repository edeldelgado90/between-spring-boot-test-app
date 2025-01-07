package com.between.springboot.application.mapper.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class PriceDTOTest {

  private final Validator validator;

  public PriceDTOTest() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  @Test
  public void testPriceDTOCreation() {
    LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

    PriceDTO priceDTO =
        PriceDTO.builder()
            .id(1L)
            .brandId(1L)
            .startDate(startDate)
            .endDate(endDate)
            .priceList(1L)
            .productId(35455L)
            .priority(1)
            .price(BigDecimal.valueOf(100.00))
            .curr("EUR")
            .build();

    assertThat(priceDTO).isNotNull();
    assertThat(priceDTO.getId()).isEqualTo(1L);
    assertThat(priceDTO.getBrandId()).isEqualTo(1L);
    assertThat(priceDTO.getStartDate()).isEqualTo(startDate);
    assertThat(priceDTO.getEndDate()).isEqualTo(endDate);
    assertThat(priceDTO.getPriceList()).isEqualTo(1L);
    assertThat(priceDTO.getProductId()).isEqualTo(35455L);
    assertThat(priceDTO.getPriority()).isEqualTo(1);
    assertThat(priceDTO.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(100.00));
    assertThat(priceDTO.getCurr()).isEqualTo("EUR");
  }

  @Test
  public void testBrandIdNotNull() {
    PriceDTO priceDTO =
        PriceDTO.builder()
            .brandId(null)
            .startDate(LocalDateTime.of(2023, 1, 1, 0, 0))
            .endDate(LocalDateTime.of(2023, 12, 31, 23, 59))
            .priceList(1L)
            .productId(35455L)
            .priority(1)
            .price(BigDecimal.valueOf(100.00))
            .curr("EUR")
            .build();

    Set<ConstraintViolation<PriceDTO>> validate = validator.validate(priceDTO);
    assertThat(validate).hasSize(1);
    assertThat(validate.iterator().next().getMessage()).isEqualTo("{price.brandId.notNull}");
  }

  @Test
  public void testStartDateCannotBeNull() {
    PriceDTO priceDTO =
        PriceDTO.builder()
            .brandId(1L)
            .startDate(null)
            .endDate(LocalDateTime.of(2023, 12, 31, 23, 59))
            .priceList(1L)
            .productId(35455L)
            .priority(1)
            .price(BigDecimal.valueOf(100.00))
            .curr("EUR")
            .build();

    Set<ConstraintViolation<PriceDTO>> validate = validator.validate(priceDTO);
    assertThat(validate).hasSize(1);
    assertThat(validate.iterator().next().getMessage()).isEqualTo("{price.startDate.notNull}");
  }

  @Test
  public void testEndDateCannotBeNull() {
    PriceDTO priceDTO =
        PriceDTO.builder()
            .brandId(1L)
            .startDate(LocalDateTime.of(2023, 1, 1, 0, 0))
            .endDate(null)
            .priceList(1L)
            .productId(35455L)
            .priority(1)
            .price(BigDecimal.valueOf(100.00))
            .curr("EUR")
            .build();

    Set<ConstraintViolation<PriceDTO>> validate = validator.validate(priceDTO);
    assertThat(validate).hasSize(1);
    assertThat(validate.iterator().next().getMessage()).isEqualTo("{price.endDate.notNull}");
  }

  @Test
  public void testCurrCannotBeBlank() {
    PriceDTO priceDTO =
        PriceDTO.builder()
            .brandId(1L)
            .startDate(LocalDateTime.of(2023, 1, 1, 0, 0))
            .endDate(LocalDateTime.of(2023, 12, 31, 23, 59))
            .priceList(1L)
            .productId(35455L)
            .priority(1)
            .price(BigDecimal.valueOf(100.00))
            .curr("")
            .build();

    Set<ConstraintViolation<PriceDTO>> validate = validator.validate(priceDTO);
    assertThat(validate).hasSize(1);
    assertThat(validate.iterator().next().getMessage()).isEqualTo("{price.curr.notBlank}");
  }
}
