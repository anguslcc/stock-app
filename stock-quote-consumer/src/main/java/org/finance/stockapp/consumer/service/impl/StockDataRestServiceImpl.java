package org.finance.stockapp.consumer.service.impl;

import org.finance.common.payload.StockDataRequest;
import org.finance.stockapp.consumer.service.StockDataRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryException;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;


@Service
public class StockDataRestServiceImpl implements StockDataRestService {

  private static final Logger LOG = LoggerFactory.getLogger(StockDataRestServiceImpl.class);

  private final WebClient webClient;
  private final RetryTemplate retryTemplate;


  public StockDataRestServiceImpl(@Qualifier("stockAppWebClient") WebClient webClient,
      RetryTemplate retryTemplate) {
    this.webClient = webClient;
    this.retryTemplate = retryTemplate;
  }

  @Override
  public void saveStockData(StockDataRequest stockDataRequest) {
    if (LOG.isInfoEnabled()) {
      LOG.info("Calling REST API for saving stock record: {}", stockDataRequest.toKeyString());
    }

    try {
      retryTemplate.execute(
          retryContext -> saveStockDataWithRetry(stockDataRequest, retryContext)
      );
    } catch (Exception e) {
      LOG.error("Maximum number of retries has been reached for saving stock record: {}",
          stockDataRequest.toKeyString());
      throw new RetryException(
          String.format("Maximum number of retries has been reached. Error: %s", e.getMessage()));
    }

    if (LOG.isInfoEnabled()) {
      LOG.info("Completed for saving stock record: {}", stockDataRequest.toKeyString());
    }

  }

  private ResponseEntity<Void> saveStockDataWithRetry(StockDataRequest stockDataRequest,
      RetryContext retryContext) {
    return webClient.post()
        .uri("/stock-data/stocks")
        .body(Mono.just(stockDataRequest), StockDataRequest.class)
        .retrieve()
        .onStatus(HttpStatus.CONFLICT::equals, clientResponse -> {
          LOG.info(
              "Stock record ({}) already exists. Request ignored.",
              stockDataRequest.toKeyString()
          );
          return Mono.empty();
        })
        .toBodilessEntity()
        .doOnError(WebClientException.class,
            ex -> LOG.info(
                "Waiting for Stock Data Service ready. Stock record: {}, Retry Count: {}",
                stockDataRequest.toKeyString(),
                retryContext.getRetryCount()))
        .block();
  }

}
