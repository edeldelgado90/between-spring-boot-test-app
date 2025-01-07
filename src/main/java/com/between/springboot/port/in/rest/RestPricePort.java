package com.between.springboot.port.in.rest;

import com.between.springboot.application.mapper.dto.PriceDTO;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface RestPricePort extends RestPort<PriceDTO> {
  Mono<PriceDTO> getCurrentPrice(Long productId, Long brandId, LocalDateTime date);

  Mono<Page<PriceDTO>> findAll(Pageable pageable);
}
