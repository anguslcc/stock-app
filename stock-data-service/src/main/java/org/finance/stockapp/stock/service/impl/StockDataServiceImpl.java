package org.finance.stockapp.stock.service.impl;

import org.finance.common.payload.StockDataRequest;
import org.finance.stockapp.stock.entity.StockInfo;
import org.finance.stockapp.stock.repository.StockInfoRepository;
import org.finance.stockapp.stock.service.StockDataService;
import org.springframework.stereotype.Service;

@Service
public class StockDataServiceImpl implements StockDataService {

  private final StockInfoRepository stockInfoRepository;

  public StockDataServiceImpl(StockInfoRepository stockInfoRepository) {
    this.stockInfoRepository = stockInfoRepository;
  }

  @Override
  public StockInfo saveStockData(StockDataRequest stockDataRequest) {
    StockInfo stockInfo = StockInfo.valueOf(stockDataRequest);
    return stockInfoRepository.save(stockInfo);
  }

}
