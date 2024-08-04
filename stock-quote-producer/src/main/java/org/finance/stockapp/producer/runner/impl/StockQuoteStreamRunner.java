package org.finance.stockapp.producer.runner.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.finance.config.kafka.KafkaConfigData;
import org.finance.config.stock.StockQuoteProducerConfigData;
import org.finance.stockapp.producer.external.payload.MarketDataResponse;
import org.finance.stockapp.producer.external.service.MarketDataRequestService;
import org.finance.stockapp.producer.transformer.MarketDataToAvroTransformer;
import org.finance.stockapp.producer.exception.StockQuoteProducerException;
import org.message.queue.kafka.model.avro.StockQuoteAvroModel;
import org.message.queue.kafka.producer.service.KafkaProducer;
import org.finance.stockapp.producer.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StockQuoteStreamRunner implements StreamRunner {

  private static final Logger LOG = LoggerFactory.getLogger(StockQuoteStreamRunner.class);

  private final KafkaConfigData kafkaConfigData;

  private final StockQuoteProducerConfigData stockQuoteProducerConfigData;

  private final KafkaProducer<Long, StockQuoteAvroModel> kafkaProducer;

  private final MarketDataRequestService marketDataService;

  public StockQuoteStreamRunner(KafkaConfigData kafkaConfigData,
      StockQuoteProducerConfigData stockQuoteProducerConfigData,
      KafkaProducer<Long, StockQuoteAvroModel> kafkaProducer,
      MarketDataRequestService marketDataService) {
    this.kafkaConfigData = kafkaConfigData;
    this.stockQuoteProducerConfigData = stockQuoteProducerConfigData;
    this.kafkaProducer = kafkaProducer;
    this.marketDataService = marketDataService;
  }

  @Override
  public void start() throws StockQuoteProducerException {
    LOG.info("Start sending Stock Quote to Kafka");
    startStockQuoteStream();
  }

  private void startStockQuoteStream() {
    try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
      executorService.submit(() -> {
        long counter = 1L;
        while (!Thread.currentThread().isInterrupted()) {
          LOG.info("Creating Stock Quote Start");
          List<MarketDataResponse> marketDataResponseList = marketDataService.getMarketData();
          List<StockQuoteAvroModel> stockQuoteAvroModelList = new ArrayList<>();
          for (MarketDataResponse marketDataResponse : marketDataResponseList) {
            stockQuoteAvroModelList.addAll(MarketDataToAvroTransformer.from(marketDataResponse));
          }

          for (StockQuoteAvroModel stockQuoteAvroModel : stockQuoteAvroModelList) {
            kafkaProducer.send(kafkaConfigData.getTopicName(), counter++, stockQuoteAvroModel);
          }

          LOG.info("Sent Stock Quote to Kafka");
          sleep(stockQuoteProducerConfigData.getSleepTime());
        }
      });
    }

  }

  private void sleep(long sleepTimeMs) {
    try {
      LOG.info("Sleep for {} ms", sleepTimeMs);
      Thread.sleep(sleepTimeMs);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new StockQuoteProducerException(
          "Error while sleeping for waiting stock quote data to create!!");
    }
  }

}
