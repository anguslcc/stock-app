package org.finance.stockapp.producer.external.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.finance.config.twelve.TwelveConfigData;
import org.finance.stockapp.producer.external.payload.MarketDataResponse;
import org.finance.stockapp.producer.external.service.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MakerDataServiceImpl implements MarketDataService {

  private static final Logger LOG = LoggerFactory.getLogger(MakerDataServiceImpl.class);
  private final RestTemplate restTemplate;

  private final TwelveConfigData twelveConfigData;

  public MakerDataServiceImpl(RestTemplate restTemplate, TwelveConfigData twelveConfigData) {
    this.restTemplate = restTemplate;
    this.twelveConfigData = twelveConfigData;
  }

  @Override
  public List<MarketDataResponse> getMarketData() {
    LOG.info("twelveConfigData: {}", twelveConfigData);

    List<MarketDataResponse> marketDataResponseList = new ArrayList<>();
    Map<String, String> parameterMap = buildBaseParameterMap();

    for (String symbol : twelveConfigData.getSymbols()) {
      parameterMap.put("symbol", symbol);
      ResponseEntity<MarketDataResponse> response = restTemplate.getForEntity(
          twelveConfigData.getUrl(),
          MarketDataResponse.class,
          parameterMap);

      if (response.getStatusCode().is2xxSuccessful()) {
        marketDataResponseList.add(response.getBody());
      } else {
        LOG.error("Error requesting data with Status Code: {} ", response.getStatusCode());

      }

    }

    return marketDataResponseList;
  }

  private Map<String, String> buildBaseParameterMap() {
    Map<String, String> parameterMap = new HashMap<>();

    parameterMap.put("apikey", twelveConfigData.getApiKey());
    parameterMap.put("interval", twelveConfigData.getInterval());
    parameterMap.put("outputsize", String.valueOf(twelveConfigData.getOutputSize()));

    return parameterMap;
  }

}
