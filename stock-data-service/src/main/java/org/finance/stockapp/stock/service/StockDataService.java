package org.finance.stockapp.stock.service;

import java.util.List;
import org.finance.common.payload.StockDataRequest;
import org.finance.common.payload.StockDetailResponse;
import org.finance.common.payload.StockMetaResponse;

public interface StockDataService {

  void saveStockData(StockDataRequest stockDataRequest);

  StockDetailResponse getStockInfo(Integer id);

  StockDetailResponse getStockInfo(String symbol, String exchange);

  List<StockMetaResponse> getStockMetaList();
}
