package com.between.springboot.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Test App API")
                .description(
                    "A practical Spring Boot application demonstrating the implementation of a real-world solution.")
                .version("v1.0"));
  }
}
