package com.between.springboot.adapter.in.grpc;

import com.between.springboot.adapter.in.grpc.proto.GetCurrentPriceByProductAndBrandRequest;
import com.between.springboot.adapter.in.grpc.proto.PriceResponse;
import com.between.springboot.adapter.in.grpc.proto.PriceServiceGrpc;
import com.between.springboot.application.PriceService;
import com.between.springboot.domain.price.Price;
import com.between.springboot.domain.price.PriceNotFoundException;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;

@GrpcService
public class GRPCPriceService extends PriceServiceGrpc.PriceServiceImplBase {
  private final PriceService priceService;

  public GRPCPriceService(PriceService priceService) {
    this.priceService = priceService;
  }

  @Override
  public void getCurrentPriceByProductAndBrand(
      GetCurrentPriceByProductAndBrandRequest request,
      StreamObserver<PriceResponse> responseObserver) {
    Timestamp timestamp = request.getDate();
    Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    Mono<Price> currentPrice =
        priceService.getCurrentPriceByProductAndBrand(
            request.getProductId(), request.getBrandId(), date);

    currentPrice
        .map(
            price -> {
              PriceResponse response = PriceResponseMapper.toResponse(price);
              responseObserver.onNext(response);
              responseObserver.onCompleted();
              return price;
            })
        .onErrorResume(
            PriceNotFoundException.class,
            ex -> {
              responseObserver.onError(
                  new StatusRuntimeException(Status.NOT_FOUND.withDescription(ex.getMessage())));
              return Mono.empty();
            })
        .subscribe();
  }
}
