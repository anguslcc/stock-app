package org.finance.stockapp.stock.service;

import org.finance.stockapp.stock.entity.IntervalEntity;
import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.finance.stockapp.stock.entity.StockIntervalPriceEntity;


public interface StockTransactionService {

  StockInfoEntity saveStockInfo(StockInfoEntity stockInfoEntity);

  IntervalEntity saveInterval(IntervalEntity intervalEntity);

  StockIntervalPriceEntity saveStockIntervalPrice(
      StockIntervalPriceEntity stockIntervalPriceEntity);
}
