package org.finance.stockapp.consumer.service;

import org.finance.common.payload.StockDataRequest;

public interface StockDataRestService {

  void saveStockData(StockDataRequest stockDataRequest);
}
