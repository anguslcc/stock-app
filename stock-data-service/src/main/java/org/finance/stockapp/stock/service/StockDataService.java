package org.finance.stockapp.stock.service;

import org.finance.common.payload.StockDataRequest;
import org.finance.stockapp.stock.entity.StockInfoEntity;

public interface StockDataService {

  void saveStockData(StockDataRequest stockDataRequest);

  StockInfoEntity getStockInfo(Integer id);
}
