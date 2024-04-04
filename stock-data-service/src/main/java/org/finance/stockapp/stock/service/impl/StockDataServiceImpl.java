package org.finance.stockapp.stock.service.impl;

import org.finance.common.payload.StockDataRequest;
import org.finance.stockapp.stock.entity.IntervalEntity;
import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.finance.stockapp.stock.extractor.StockDataRequestExtractor;
import org.finance.stockapp.stock.repository.IntervalRepository;
import org.finance.stockapp.stock.repository.StockInfoRepository;
import org.finance.stockapp.stock.repository.StockIntervalPriceRepository;
import org.finance.stockapp.stock.service.StockDataService;
import org.springframework.stereotype.Service;

@Service
public class StockDataServiceImpl implements StockDataService {

  private final StockInfoRepository stockInfoRepository;

  private final IntervalRepository intervalRepository;

  private final StockIntervalPriceRepository stockIntervalPriceRepository;

  public StockDataServiceImpl(StockInfoRepository stockInfoRepository,
      IntervalRepository intervalRepository,
      StockIntervalPriceRepository stockIntervalPriceRepository) {
    this.stockInfoRepository = stockInfoRepository;
    this.intervalRepository = intervalRepository;
    this.stockIntervalPriceRepository = stockIntervalPriceRepository;
  }

  @Override
  public void saveStockData(StockDataRequest stockDataRequest) {
    StockDataRequestExtractor stockDataRequestExtractor = new StockDataRequestExtractor(
        stockDataRequest);

    StockInfoEntity stockInfoEntity = stockInfoRepository.save(
        stockDataRequestExtractor.getStockInfoEntity());
    IntervalEntity intervalEntity = intervalRepository.save(
        stockDataRequestExtractor.getIntervalEntity());

    stockIntervalPriceRepository.save(
        stockDataRequestExtractor.getStockIntervalPriceEntity(stockInfoEntity.getId(),
            intervalEntity.getId())
    );

  }

  @Override
  public StockInfoEntity getStockInfo(Integer id) {
    return stockInfoRepository.findById(id).orElse(null);
  }

}
