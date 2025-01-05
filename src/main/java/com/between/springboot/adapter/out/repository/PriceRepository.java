package com.between.springboot.adapter.out.repository;

import com.between.springboot.adapter.out.model.PriceEntity;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PriceRepository extends ReactiveCrudRepository<PriceEntity, Long> {

  @Query(
      "SELECT p.* FROM prices p WHERE p.brand_id = :brandId AND p.product_id = :productId AND p.start_date <= :date AND p.end_date >= :date ")
  Flux<PriceEntity> findByProductIdAndBrandIdAndDate(
      Long productId, Long brandId, LocalDateTime date);

  Flux<PriceEntity> findAllBy(Pageable pageable);
}
