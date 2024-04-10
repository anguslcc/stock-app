package org.finance.stockapp.producer.external.service.impl;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.finance.config.api.ApiKeyConfigData;
import org.finance.config.twelve.TwelveConfigData;
import org.finance.stockapp.producer.external.payload.MarketDataResponse;
import org.finance.stockapp.producer.external.service.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MakerDataServiceImpl implements MarketDataService {

  private static final Logger LOG = LoggerFactory.getLogger(MakerDataServiceImpl.class);
  private final WebClient webClient;

  private final TwelveConfigData twelveConfigData;

  private final ApiKeyConfigData apiKeyConfigData;


  public MakerDataServiceImpl(
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

      if (response != null && response.getStatusCode().is2xxSuccessful()) {
        marketDataResponseList.add(response.getBody());
      } else {
        if (response != null) {
          LOG.error("Error requesting data with Status Code: {} ", response.getStatusCode());
        } else {
          LOG.error("Error requesting data with Null response");

        }
      }

    }

    return marketDataResponseList;
  }

  private ResponseEntity<MarketDataResponse> getMarketDataResponse(String symbol) {
    return webClient.get()
        .uri(uriBuilder -> {
              URI uri = uriBuilder
                  .scheme(twelveConfigData.getScheme())
                  .host(twelveConfigData.getHost())
                  .path(twelveConfigData.getPath())
                  .queryParam("apikey", apiKeyConfigData.getTwelve())
                  .queryParam("interval", twelveConfigData.getInterval())
                  .queryParam("symbol", symbol)
                  .queryParam("format", "JSON")
                  .queryParam("outputsize", twelveConfigData.getOutputSize())
                  .build();
              LOG.info("uri {} ", uri);
              return uri;
            }
        )
        .retrieve()
        .toEntity(MarketDataResponse.class)
        .doOnError(e -> LOG.info("Error: {} ", e.getMessage()))
        .timeout(Duration.ofSeconds(10))
        .block();
  }


}
