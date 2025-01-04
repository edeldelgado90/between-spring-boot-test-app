package com.between.springboot.port.out;

import reactor.core.publisher.Mono;

public interface DatabasePort<T> {

  Mono<T> save(T price);

  Mono<Void> delete(Long id);
}
