package com.between.springboot.port.out;

import com.between.springboot.domain.price.Price;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DatabasePort<T> {

  Mono<T> save(T price);

  Mono<Void> delete(Long id);

  Mono<Page<Price>> findAllBy(Pageable pageable);
}
