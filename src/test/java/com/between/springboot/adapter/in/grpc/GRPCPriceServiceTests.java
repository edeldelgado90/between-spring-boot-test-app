package com.between.springboot.adapter.in.grpc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.between.springboot.adapter.in.grpc.proto.GetCurrentPriceRequest;
import com.between.springboot.adapter.in.grpc.proto.PriceResponse;
import com.between.springboot.adapter.in.grpc.proto.PriceServiceGrpc;
import com.between.springboot.application.PriceService;
import com.google.protobuf.Timestamp;
import io.grpc.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GRPCPriceServiceTests {

  @Autowired private PriceService priceService;

  private ManagedChannel channel;
  private PriceServiceGrpc.PriceServiceBlockingStub blockingStub;

  @BeforeEach
  public void setUp() throws IOException {
    Server server =
        ServerBuilder.forPort(9090).addService(new GRPCPriceService(priceService)).build().start();
    channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
    blockingStub = PriceServiceGrpc.newBlockingStub(channel);
  }

  @AfterEach
  public void tearDown() {
    channel.shutdown();
  }

  @Test
  public void testGetCurrentPrice() {
    long productId = 1;
    long brandId = 1;
    String testDate = "2023-01-01T00:00:00";

    GetCurrentPriceRequest request =
        GetCurrentPriceRequest.newBuilder()
            .setProductId(productId)
            .setBrandId(brandId)
            .setDate(
                Timestamp.newBuilder()
                    .setSeconds(
                        LocalDateTime.parse(testDate)
                            .atZone(ZoneId.systemDefault())
                            .toEpochSecond())
                    .setNanos(LocalDateTime.parse(testDate).getNano())
                    .build())
            .build();

    PriceResponse response = blockingStub.getCurrentPrice(request);

    assertThat(response).isNotNull();
    assertThat(response.getProductId()).isEqualTo(productId);
    assertThat(response.getBrandId()).isEqualTo(brandId);
  }

  @Test
  public void testGetCurrentPriceNotFound() {
    long productId = 999;
    long brandId = 999;
    String testDate = "2023-01-01T00:00:00";

    GetCurrentPriceRequest request =
        GetCurrentPriceRequest.newBuilder()
            .setProductId(productId)
            .setBrandId(brandId)
            .setDate(
                Timestamp.newBuilder()
                    .setSeconds(
                        LocalDateTime.parse(testDate)
                            .atZone(ZoneId.systemDefault())
                            .toEpochSecond())
                    .setNanos(LocalDateTime.parse(testDate).getNano())
                    .build())
            .build();

    assertThatThrownBy(() -> blockingStub.getCurrentPrice(request))
        .isInstanceOf(StatusRuntimeException.class)
        .hasMessageContaining("No price found for the given criteria.");
  }
}
