package org.finance.stockapp.stock.extractor;

import org.finance.common.payload.StockDataRequest;
import org.finance.stockapp.stock.entity.IntervalEntity;
import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.finance.stockapp.stock.entity.StockIntervalPriceEntity;
import org.finance.stockapp.stock.entity.enums.IntervalUnit;

public class StockDataRequestExtractor {

  private final StockDataRequest stockDataRequest;

  public StockDataRequestExtractor(StockDataRequest stockDataRequest) {
    this.stockDataRequest = stockDataRequest;
  }

  public StockInfoEntity getStockInfoEntity() {
    StockInfoEntity stockInfoEntity = new StockInfoEntity();
    stockInfoEntity.setSymbol(stockDataRequest.getSymbol());
    stockInfoEntity.setCurrency(stockDataRequest.getCurrency());
    stockInfoEntity.setExchange(stockDataRequest.getExchange());
    return stockInfoEntity;
  }

  public IntervalEntity getIntervalEntity() {
    IntervalEntity intervalEntity = new IntervalEntity();

    String intervalUnitStr = stockDataRequest.getInterval().replaceAll("\\d", "");
    intervalEntity.setUnit(IntervalUnit.convert(intervalUnitStr));

    String intervalValueStr = stockDataRequest.getInterval().replaceAll("[^\\d]", "");
    intervalEntity.setValue(Integer.valueOf(intervalValueStr));

    return intervalEntity;
  }

  public StockIntervalPriceEntity getStockIntervalPriceEntity(Integer stockId, Integer intervalId) {
    StockIntervalPriceEntity stockIntervalPriceEntity = new StockIntervalPriceEntity();

    stockIntervalPriceEntity.setStockId(stockId);
    stockIntervalPriceEntity.setIntervalId(intervalId);
    stockIntervalPriceEntity.setOpen(stockDataRequest.getOpen());
    stockIntervalPriceEntity.setClose(stockDataRequest.getClose());
    stockIntervalPriceEntity.setHigh(stockDataRequest.getHigh());
    stockIntervalPriceEntity.setLow(stockDataRequest.getLow());
    stockIntervalPriceEntity.setVolume(stockDataRequest.getVolume());
    stockIntervalPriceEntity.setEndTime(stockDataRequest.getEndTime());

    return stockIntervalPriceEntity;
  }

}
