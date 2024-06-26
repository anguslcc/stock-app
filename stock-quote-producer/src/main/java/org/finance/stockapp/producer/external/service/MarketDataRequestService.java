package org.finance.stockapp.producer.external.service;

import java.util.List;
import org.finance.stockapp.producer.external.payload.MarketDataResponse;

public interface MarketDataRequestService {

  List<MarketDataResponse> getMarketData();
}
