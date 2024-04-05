## Project Overview

This application is designed to efficiently process market data using a microservices architecture.
The application consists of several key components that work together to ingest, process, and store
market data.

1. Data Ingestion: The application starts with a producer microservice that queries market data from
   an external API. This microservice is responsible for retrieving and initially processing the
   data to prepare it for transmission.

2. Data Transmission: Once the data is prepared, it is sent to a consumer microservice via Apache
   Kafka. This setup leverages Kafka's robust messaging capabilities to ensure reliable and scalable
   data transfer between the microservices.

3. Data Processing and Storage: Upon receiving the data, the consumer microservice makes a REST API
   call to a dedicated data microservice. This microservice is tasked with pushing the data into a
   database, ensuring that the market data is stored securely and is accessible for further analysis
   and reporting.

## Pre-requisites

1. Docker
2. Maven
3. JDK 17

## Build Application Procedure

1. mvn install -Pbuild-image

## Start the appication

1. cd docker-compose
2. Open separate windows for executing following commands
  * docker-compose up gateway-service
  * docker-compose up stock-quote-producer
  * docker-compose up stock-quote-consumer

## Test API

- http://localhost:8080/stocks/        [List all the stocks available in the database]
- http://localhost:8080/stocks/{id}    [Get the stock price details by id]






