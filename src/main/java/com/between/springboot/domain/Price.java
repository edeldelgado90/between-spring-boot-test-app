package com.between.springboot.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Price {
  private Long id;
  private final Long brandId;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final Long priceList;
  private final Long productId;
  private final Integer priority;
  private final BigDecimal price;
  private final String curr;
}
