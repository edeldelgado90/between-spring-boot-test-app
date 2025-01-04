package com.between.springboot.adapter.in.grpc;

import com.between.springboot.adapter.in.grpc.proto.PriceServiceGrpc;
import com.between.springboot.adapter.in.grpc.proto.GetCurrentPriceRequest;
import com.between.springboot.adapter.in.grpc.proto.PriceResponse;
import com.between.springboot.application.PriceService;
import com.between.springboot.domain.Price;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GRPCPriceService extends PriceServiceGrpc.PriceServiceImplBase {
  private final PriceService priceService;

  public GRPCPriceService(PriceService priceService) {
    this.priceService = priceService;
  }

  @Override
  public void getCurrentPrice(
      GetCurrentPriceRequest request, StreamObserver<PriceResponse> responseObserver) {
    Timestamp timestamp = request.getDate();
    Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    Mono<Price> currentPrice =
        priceService.getCurrentPrice(request.getProductId(), request.getBrandId(), date);

    currentPrice.subscribe(
        price -> {
          if (price == null) {
            responseObserver.onError(
                new RuntimeException("No price found for the given criteria."));
            return;
          }
          double priceValue = price.getPrice().doubleValue();
          PriceResponse response = PriceResponse.newBuilder().setPrice(priceValue).build();
          responseObserver.onNext(response);
          responseObserver.onCompleted();
        },
        throwable -> {
          System.err.println("Error al obtener el precio: " + throwable.getMessage());
          responseObserver.onError(throwable);
        });
  }
}
