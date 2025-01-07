package com.between.springboot.application.mapper.dto;

import com.between.springboot.application.mapper.constraint.StartDateBeforeEndDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@StartDateBeforeEndDate
public class PriceDTO {
  private Long id;

  @NotNull(message = "{price.brandId.notNull}")
  @PositiveOrZero(message = "{price.brandId.PositiveOrZero}")
  private final Long brandId;

  @NotNull(message = "{price.startDate.notNull}")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private final LocalDateTime startDate;

  @NotNull(message = "{price.endDate.notNull}")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private final LocalDateTime endDate;

  @PositiveOrZero(message = "{price.priceList.notBlank}")
  private final Long priceList;

  @PositiveOrZero(message = "{price.productId.notBlank}")
  private final Long productId;

  @PositiveOrZero(message = "{price.priority.PositiveOrZero}")
  private final Integer priority;

  @PositiveOrZero(message = "{price.price.PositiveOrZero}")
  private final BigDecimal price;

  @NotBlank(message = "{price.curr.notBlank}")
  private final String curr;
}
