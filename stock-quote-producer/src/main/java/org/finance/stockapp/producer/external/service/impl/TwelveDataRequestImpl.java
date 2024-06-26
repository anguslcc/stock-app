package org.finance.stockapp.producer.external.service.impl;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.finance.config.api.ApiKeyConfigData;
import org.finance.config.twelve.TwelveConfigData;
import org.finance.stockapp.producer.external.payload.MarketDataResponse;
import org.finance.stockapp.producer.external.payload.enums.Status;
import org.finance.stockapp.producer.external.service.MarketDataRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TwelveDataRequestImpl implements MarketDataRequestService {

  private static final Logger LOG = LoggerFactory.getLogger(TwelveDataRequestImpl.class);
  private final WebClient webClient;

  private final TwelveConfigData twelveConfigData;

  private final ApiKeyConfigData apiKeyConfigData;


  public TwelveDataRequestImpl(
      @Qualifier("defaultWebClient") WebClient webClient,
      TwelveConfigData twelveConfigData,
      ApiKeyConfigData apiKeyConfigData) {
    this.webClient = webClient;
    this.twelveConfigData = twelveConfigData;
    this.apiKeyConfigData = apiKeyConfigData;
  }

  @Override
  public List<MarketDataResponse> getMarketData() {
    LOG.info("twelveConfigData: {}", twelveConfigData);
    LOG.info("apiKeyConfigData: {}", apiKeyConfigData);

    List<MarketDataResponse> marketDataResponseList = new ArrayList<>();

    for (String symbol : twelveConfigData.getSymbols()) {

      ResponseEntity<MarketDataResponse> response = getMarketDataResponse(symbol);

      if (validateMarketDataResponse(response)) {
        marketDataResponseList.add(response.getBody());
      } else {
        LOG.error("Error in marketDataResponse for {}. Please refer to log below",
            twelveConfigData.getSymbols());
      }
      printMarketDataResponse(response);

    }

    return marketDataResponseList;
  }

  private ResponseEntity<MarketDataResponse> getMarketDataResponse(String symbol) {
    return webClient.get()
        .uri(buildUri(symbol))
        .retrieve()
        .toEntity(MarketDataResponse.class)
        .doOnError(e -> LOG.error("Data Request Error: {} ", e.getMessage()))
        .timeout(Duration.ofSeconds(10))
        .block();
  }

  private URI buildUri(String symbol) {
    URI uri = UriComponentsBuilder.newInstance()
        .scheme(twelveConfigData.getScheme())
        .host(twelveConfigData.getHost())
        .path(twelveConfigData.getPath())
        .queryParam("apikey", apiKeyConfigData.getTwelve())
        .queryParam("interval", twelveConfigData.getInterval())
        .queryParam("symbol", symbol)
        .queryParam("format", "JSON")
        .queryParam("outputsize", twelveConfigData.getOutputSize())
        .build()
        .toUri();
    LOG.info("Request URI: {}", uri);
    return uri;
  }

  private boolean validateMarketDataResponse(ResponseEntity<MarketDataResponse> response) {
    boolean isValid = false;

    if (response != null && response.getStatusCode().is2xxSuccessful()) {
      MarketDataResponse marketDataResponse = response.getBody();
      if (marketDataResponse != null && Status.OK.equals(marketDataResponse.getStatus())) {
        isValid = true;
      }
    }

    return isValid;

  }

  private void printMarketDataResponse(ResponseEntity<MarketDataResponse> response) {
    if (response == null) {
      LOG.info("Response is Null");
    } else {
      LOG.info("Response Status Code : {}", response.getStatusCode().value());
      LOG.info("marketDataResponse: {} ", response.getBody());
    }
  }

}
