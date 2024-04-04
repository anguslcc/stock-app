package org.finance.stockapp.stock.service.impl;

import java.util.Optional;
import org.finance.common.payload.StockDataRequest;
import org.finance.stockapp.stock.entity.IntervalEntity;
import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.finance.stockapp.stock.entity.StockIntervalPriceId;
import org.finance.stockapp.stock.extractor.StockDataRequestExtractor;
import org.finance.stockapp.stock.repository.IntervalRepository;
import org.finance.stockapp.stock.repository.StockInfoRepository;
import org.finance.stockapp.stock.repository.StockIntervalPriceRepository;
import org.finance.stockapp.stock.service.StockDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StockDataServiceImpl implements StockDataService {

  private static final Logger LOG = LoggerFactory.getLogger(StockDataServiceImpl.class);
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
    LOG.info("Performing saveStockDate: {} ", stockDataRequest);
    StockDataRequestExtractor stockDataRequestExtractor = new StockDataRequestExtractor(
        stockDataRequest);

    Optional<StockInfoEntity> stockInfoEntityOptional = stockInfoRepository.getBy(
        stockDataRequest.getSymbol(), stockDataRequest.getExchange());

    StockInfoEntity stockInfoEntity = stockInfoEntityOptional.orElseGet(
        () -> stockInfoRepository.save(stockDataRequestExtractor.getStockInfoEntity()));

    Optional<IntervalEntity> intervalEntityOptional = intervalRepository.getBy(
        stockDataRequest.getIntervalValue(), stockDataRequest.getIntervalUnit());

    IntervalEntity intervalEntity = intervalEntityOptional.orElseGet(() -> intervalRepository.save(
        stockDataRequestExtractor.getIntervalEntity()));

    StockIntervalPriceId stockIntervalPriceId = new StockIntervalPriceId(stockInfoEntity.getId(),
        stockDataRequest.getEndTime(), intervalEntity.getId());

    if (!stockIntervalPriceRepository.existsById(stockIntervalPriceId)) {
      stockIntervalPriceRepository.save(
          stockDataRequestExtractor.getStockIntervalPriceEntity(stockInfoEntity.getId(),
              intervalEntity.getId())
      );
    }

  }

  @Override
  public StockInfoEntity getStockInfo(Integer id) {
    return stockInfoRepository.findById(id).orElse(null);
  }

}
