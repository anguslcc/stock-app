package org.finance.stockapp.stock.service.impl;

import java.util.Optional;
import org.finance.stockapp.stock.entity.IntervalEntity;
import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.finance.stockapp.stock.entity.StockIntervalPriceEntity;
import org.finance.stockapp.stock.entity.StockIntervalPriceId;
import org.finance.stockapp.stock.exception.AlreadyExistsException;
import org.finance.stockapp.stock.repository.IntervalRepository;
import org.finance.stockapp.stock.repository.StockInfoRepository;
import org.finance.stockapp.stock.repository.StockIntervalPriceRepository;
import org.finance.stockapp.stock.service.StockTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockTransactionServiceImpl implements StockTransactionService {

  private final StockInfoRepository stockInfoRepository;
  private final IntervalRepository intervalRepository;
  private final StockIntervalPriceRepository stockIntervalPriceRepository;

  public StockTransactionServiceImpl(StockInfoRepository stockInfoRepository,
      IntervalRepository intervalRepository,
      StockIntervalPriceRepository stockIntervalPriceRepository) {
    this.stockInfoRepository = stockInfoRepository;
    this.intervalRepository = intervalRepository;
    this.stockIntervalPriceRepository = stockIntervalPriceRepository;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public StockInfoEntity saveStockInfo(StockInfoEntity stockInfoEntity) {

    Optional<StockInfoEntity> stockInfoEntityOptional = stockInfoRepository.getBy(
        stockInfoEntity.getSymbol(), stockInfoEntity.getExchange());

    return stockInfoEntityOptional.orElseGet(
        () -> stockInfoRepository.save(stockInfoEntity));

  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public IntervalEntity saveInterval(IntervalEntity intervalEntity) {
    Optional<IntervalEntity> intervalEntityOptional = intervalRepository.getBy(
        intervalEntity.getValue(), intervalEntity.getUnit());

    return intervalEntityOptional.orElseGet(() -> intervalRepository.save(
        intervalEntity));
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public StockIntervalPriceEntity saveStockIntervalPrice(
      StockIntervalPriceEntity stockIntervalPriceEntity) {
    StockIntervalPriceId stockIntervalPriceId = new StockIntervalPriceId(
        stockIntervalPriceEntity.getStockId(),
        stockIntervalPriceEntity.getEndTime(), stockIntervalPriceEntity.getIntervalId());

    Optional<StockIntervalPriceEntity> stockIntervalPriceEntityOptional = stockIntervalPriceRepository.findById(
        stockIntervalPriceId);

    if (stockIntervalPriceEntityOptional.isPresent()) {
      throw new AlreadyExistsException("Stock Interval Price already existed in the database");
    } else {
      return stockIntervalPriceRepository.save(stockIntervalPriceEntity);
    }

  }
}
