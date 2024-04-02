package org.finance.stock.external.service;

import java.util.List;
import org.finance.stock.external.payload.MarketDataResponse;

public interface MarketDataService {

  List<MarketDataResponse> getMarketData();
}
