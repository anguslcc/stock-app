package org.finance.stockapp.stock.service;

import java.util.List;
import org.finance.common.payload.StockDataRequest;
import org.finance.common.payload.StockMetaResponse;
import org.finance.stockapp.stock.entity.StockInfoEntity;

public interface StockDataService {

  void saveStockData(StockDataRequest stockDataRequest);

  StockInfoEntity getStockInfo(Integer id);

  List<StockMetaResponse> getStockMetaList();
}
