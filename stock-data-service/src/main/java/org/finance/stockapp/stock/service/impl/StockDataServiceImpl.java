package org.finance.stockapp.stock.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import org.finance.common.payload.StockDataRequest;
import org.finance.common.payload.StockDetailResponse;
import org.finance.common.payload.StockIntervalData;
import org.finance.common.payload.StockMetaResponse;
import org.finance.stockapp.stock.entity.IntervalEntity;
import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.finance.stockapp.stock.entity.StockIntervalPriceEntity;
import org.finance.stockapp.stock.exception.NotFoundException;
import org.finance.stockapp.stock.extractor.StockDataRequestExtractor;
import org.finance.stockapp.stock.repository.StockInfoRepository;
import org.finance.stockapp.stock.service.StockDataService;
import org.finance.stockapp.stock.service.StockTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockDataServiceImpl implements StockDataService {

  private static final Logger LOG = LoggerFactory.getLogger(StockDataServiceImpl.class);
  private final StockInfoRepository stockInfoRepository;
  private final StockTransactionService stockTransactionService;

  public StockDataServiceImpl(StockInfoRepository stockInfoRepository,
      StockTransactionService stockTransactionService) {
    this.stockInfoRepository = stockInfoRepository;
    this.stockTransactionService = stockTransactionService;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void saveStockData(StockDataRequest stockDataRequest) {
    LOG.info("Performing saveStockDate: {} ", stockDataRequest);
    StockDataRequestExtractor stockDataRequestExtractor = new StockDataRequestExtractor(
        stockDataRequest);

    StockInfoEntity stockInfoEntity = stockTransactionService.saveStockInfo(
        stockDataRequestExtractor.getStockInfoEntity());

    IntervalEntity intervalEntity = stockTransactionService.saveInterval(
        stockDataRequestExtractor.getIntervalEntity());

    stockTransactionService.saveStockIntervalPrice(
        stockDataRequestExtractor.getStockIntervalPriceEntity(stockInfoEntity.getId(),
            intervalEntity.getId())
    );

  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public StockDetailResponse getStockInfo(Integer id) {
    StockInfoEntity stockInfoEntity = stockInfoRepository.findById(id).orElseThrow(
        () -> new NotFoundException(
            "Sorry, the requested stock record (id: {0}) could not be found.", id));

    return buildStockDetailResponse(stockInfoEntity);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public StockDetailResponse getStockInfo(String symbol, String exchange) {
    StockInfoEntity stockInfoEntity = stockInfoRepository.getBy(symbol, exchange).orElseThrow(
        () -> new NotFoundException(
            "Sorry, the requested stock record (symbol: {0}, exchange: {1}) could not be found.",
            symbol, exchange));

    return buildStockDetailResponse(stockInfoEntity);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<StockMetaResponse> getStockMetaList() {
    return stockInfoRepository.findAll().stream().map(StockInfoEntity::toStockMetaResponse)
        .toList();
  }

  private StockIntervalData buildAggregatedStockIntervalData(
      List<StockIntervalPriceEntity> stockPriceList) {
    List<StockIntervalPriceEntity> inputList = stockPriceList.stream()
        .sorted(Comparator.comparing(StockIntervalPriceEntity::getEndTime)).toList();

    double high = inputList.stream()
        .mapToDouble(StockIntervalPriceEntity::getHigh)
        .max()
        .orElse(0);

    double low = inputList.stream()
        .mapToDouble(StockIntervalPriceEntity::getLow)
        .min()
        .orElse(0);

    int volume = inputList.stream()
        .mapToInt(StockIntervalPriceEntity::getVolume)
        .sum();

    int intervalValue = inputList.stream()
        .mapToInt(x -> x.getIntervalEntity().getValue())
        .sum();

    LocalDateTime endTime = inputList.get(inputList.size() - 1).getEndTime();

    LocalDateTime startTime = endTime.minusMinutes(intervalValue);

    return StockIntervalData.newBuilder()
        .setOpen(inputList.get(0).getOpen())
        .setClose(inputList.get(inputList.size() - 1).getClose())
        .setHigh(high)
        .setLow(low)
        .setVolume(volume)
        .setStartTime(startTime)
        .setEndTime(endTime)
        .build();
  }

  private StockIntervalData getLatestStockIntervalData(
      List<StockIntervalPriceEntity> stockPriceList) {
    StockIntervalPriceEntity stockIntervalPrice = stockPriceList.stream()
        .max(Comparator.comparing(StockIntervalPriceEntity::getEndTime))
        .orElse(new StockIntervalPriceEntity());

    return StockIntervalData.newBuilder()
        .setOpen(stockIntervalPrice.getOpen())
        .setClose(stockIntervalPrice.getClose())
        .setHigh(stockIntervalPrice.getHigh())
        .setLow(stockIntervalPrice.getLow())
        .setVolume(stockIntervalPrice.getVolume())
        .setStartTime(stockIntervalPrice.getEndTime().minusMinutes(1))
        .setEndTime(stockIntervalPrice.getEndTime())
        .build();

  }

  private StockDetailResponse buildStockDetailResponse(StockInfoEntity stockInfoEntity) {
    StockDetailResponse stockDetailResponse = new StockDetailResponse();
    stockDetailResponse.setMeta(stockInfoEntity.toStockMetaResponse());
    stockDetailResponse.setLatest(
        getLatestStockIntervalData(stockInfoEntity.getStockIntervalPriceEntityList()));
    stockDetailResponse.setAggregated(
        buildAggregatedStockIntervalData(stockInfoEntity.getStockIntervalPriceEntityList()));

    return stockDetailResponse;
  }

}
