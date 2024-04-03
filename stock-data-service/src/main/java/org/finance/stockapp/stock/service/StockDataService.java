package org.finance.stockapp.stock.service;

import org.finance.common.payload.StockDataRequest;
import org.finance.stockapp.stock.entity.StockInfo;

public interface StockDataService {

  StockInfo saveStockData(StockDataRequest stockDataRequest);
}
