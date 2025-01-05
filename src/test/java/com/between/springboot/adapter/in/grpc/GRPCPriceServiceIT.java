package com.between.springboot.adapter.in.grpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.between.springboot.adapter.in.grpc.proto.GetCurrentPriceByProductAndBrandRequest;
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
public class GRPCPriceServiceIT {

  @Autowired private PriceService priceService;
  private ManagedChannel channel;
  private PriceServiceGrpc.PriceServiceBlockingStub blockingStub;

  @BeforeEach
  public void setUp() throws IOException {
    channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
    blockingStub = PriceServiceGrpc.newBlockingStub(channel);
  }

  @AfterEach
  public void tearDown() throws InterruptedException {
    channel.shutdown();
  }

  @Test
  public void testGetCurrentPrice() {
    long productId = 35455;
    long brandId = 1;
    String testDate = "2020-07-01T12:00:00";

    GetCurrentPriceByProductAndBrandRequest request =
        GetCurrentPriceByProductAndBrandRequest.newBuilder()
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

    PriceResponse response = blockingStub.getCurrentPriceByProductAndBrand(request);

    assertThat(response).isNotNull();
    assertThat(response.getPrice()).isEqualByComparingTo(38.95);
  }
}
