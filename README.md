# between-spring-boot-test-app

A practical Spring Boot application demonstrating the implementation of a real-world solution.

## Description

This is a Spring Boot application that implements a price service using WebFlux and gRPC to provide a reactive API that
manages price operations.
The application is connected to an in-memory H2 database with migrations managed by Flyway.

## Features

- **REST API**: For HTTP operations using WebFlux.
- **gRPC**: For asynchronous and protocol-based interactions from clients.
- **Database**: Uses H2 in-memory for testing and development.
- **Migrations**: Automatic database schema management with Flyway.

## Requirements

- JDK 21 or higher
- Maven
- Docker (optional, if you want containers)

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/edeldelgado90/between-spring-boot-test-app.git
   cd between-spring-boot-test-app
   ```
2. **Build the Project**:
    ```bash
    mvn clean install
    ```
3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
4. **Access the H2 Console**:
   You can access the H2 console at:
    ```
    http://localhost:8080/h2-console
    ```
   Use the following connection URL:
    ```
    jdbc:h2:mem:testdb
    ```

## REST API Endpoints

### Create a Price

#### POST `/api/prices`

Example Request Body:

```json
{
  "brand_id": 1,
  "start_date": "2023-01-01T00:00:00",
  "end_date": "2023-12-31T23:59:59",
  "price_list": 1,
  "product_id": 35455,
  "priority": 1,
  "price": 100.00,
  "curr": "EUR"
}
```

Curl Example:

```bash
curl -X POST http://localhost:8080/api/prices \
  -H "Content-Type: application/json" \
  -d '{
        "brandId": 1,
        "startDate": "2023-01-01T00:00:00",
        "endDate": "2023-12-31T23:59:59",
        "priceList": 1,
        "productId": 35455,
        "priority": 1,
        "price": 100.00,
        "curr": "EUR"
      }'
```

### Get Current Price

#### GET `/api/prices/current?product_id={id}&brand_id={id}&date={date}`

Curl Example:

```bash
curl -X GET http://localhost:8080/api/prices/current?product_id=35455&brand_id=1&date=2020-07-01T12:00:00 \
  -H "Content-Type: application/json"
```

### Delete a Price

#### DELETE `/api/prices/{id}`

Curl Example:

```bash
curl -X DELETE http://localhost:8080/api/prices/1 \
  -H "Content-Type: application/json"
```

## gRPC Usage

To use gRPC, ensure you have grpcurl installed and use the following command to invoke the service:

```bash
grpcurl -plaintext -d '{
    "productId": 35455,
    "brandId": 1,
    "date": "2023-01-01T00:00:00Z"
}' localhost:9090 prices.PriceService/GetCurrentPriceByProductAndBrand
```

## Running Tests

To run unit and integration tests:

```bash
mvn test
```
