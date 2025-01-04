package com.between.springboot.adapter.out;

import com.between.springboot.adapter.out.model.PriceEntity;
import com.between.springboot.adapter.out.repository.PriceRepository;
import com.between.springboot.application.mapper.PriceMapper;
import com.between.springboot.domain.Price;
import com.between.springboot.port.out.DatabasePricePort;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DatabasePriceAdapter implements DatabasePricePort {

  private final PriceRepository repository;
  private final PriceMapper mapper;

  public DatabasePriceAdapter(PriceRepository repository, PriceMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Mono<Price> save(Price price) {
    PriceEntity priceEntity = mapper.toEntity(price);
    Mono<PriceEntity> response = repository.save(priceEntity);
    return response.map(mapper::toModel);
  }

  @Override
  public Flux<Price> getPrices(Long productId, Long brandId, LocalDateTime date) {
    return repository
        .findByProductIdAndBrandIdAndDate(productId, brandId, date)
        .map(mapper::toModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return repository.deleteById(id);
  }
}
