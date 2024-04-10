package org.finance.stockapp.stock.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.finance.common.payload.StockDataRequest;
import org.finance.common.payload.StockDetailResponse;
import org.finance.common.payload.StockIntervalData;
import org.finance.common.payload.StockMetaResponse;
import org.finance.stockapp.stock.entity.IntervalEntity;
import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.finance.stockapp.stock.entity.StockIntervalPriceEntity;
import org.finance.stockapp.stock.entity.StockIntervalPriceId;
import org.finance.stockapp.stock.exception.NotFoundException;
import org.finance.stockapp.stock.extractor.StockDataRequestExtractor;
import org.finance.stockapp.stock.repository.IntervalRepository;
import org.finance.stockapp.stock.repository.StockInfoRepository;
import org.finance.stockapp.stock.repository.StockIntervalPriceRepository;
import org.finance.stockapp.stock.service.StockDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional(propagation = Propagation.REQUIRED)
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
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public StockDetailResponse getStockInfo(Integer id) {
    StockDetailResponse stockDetailResponse = new StockDetailResponse();

    StockInfoEntity stockInfoEntity = stockInfoRepository.findById(id).orElseThrow(
        () -> new NotFoundException("Stock not found"));

    stockDetailResponse.setMeta(stockInfoEntity.toStockMetaResponse());
    stockDetailResponse.setLatest(
        getLatestStockIntervalData(stockInfoEntity.getStockIntervalPriceEntityList()));
    stockDetailResponse.setAggregated(
        buildAggregatedStockIntervalData(stockInfoEntity.getStockIntervalPriceEntityList()));

    return stockDetailResponse;
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

    Double high = inputList.stream()
        .mapToDouble(StockIntervalPriceEntity::getHigh)
        .max()
        .orElse(0);

    Double low = inputList.stream()
        .mapToDouble(StockIntervalPriceEntity::getLow)
        .min()
        .orElse(0);

    Integer volume = inputList.stream()
        .mapToInt(StockIntervalPriceEntity::getVolume)
        .sum();

    Integer intervalValue = inputList.stream()
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
        .sorted(Comparator.comparing(StockIntervalPriceEntity::getEndTime).reversed())
        .findFirst()
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

}
