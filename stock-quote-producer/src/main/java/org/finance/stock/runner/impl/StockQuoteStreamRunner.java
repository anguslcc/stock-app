package org.finance.stock.runner.impl;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.Executors;
import org.finance.config.kafka.KafkaConfigData;
import org.message.queue.kafka.model.avro.StockQuoteAvroModel;
import org.message.queue.kafka.producer.service.KafkaProducer;
import org.finance.stock.exception.StockQuoteProducerException;
import org.finance.stock.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StockQuoteStreamRunner implements StreamRunner {

  private static final Logger LOG = LoggerFactory.getLogger(StockQuoteStreamRunner.class);

  private final KafkaConfigData kafkaConfigData;

  private final KafkaProducer<Long, StockQuoteAvroModel> kafkaProducer;

  public StockQuoteStreamRunner(KafkaConfigData kafkaConfigData,
      KafkaProducer<Long, StockQuoteAvroModel> kafkaProducer) {
    this.kafkaConfigData = kafkaConfigData;
    this.kafkaProducer = kafkaProducer;
  }

  @Override
  public void start() throws StockQuoteProducerException {
    LOG.info("Start sending Quote to Kafka");
    simulateStockQuoteStream();
  }

  private void simulateStockQuoteStream() {
    Executors.newSingleThreadExecutor().submit(() -> {
      Random rand = new Random();
      while (true) {
        LOG.info("Creating Stock Quote Start");
        double high = 10.21;
        double low = 7.21;
        double bid = low + rand.nextDouble(high - low);
        double offer = bid + 0.1;
        DecimalFormat f = new DecimalFormat("##.00");
        StockQuoteAvroModel stockQuoteAvroModel = StockQuoteAvroModel
            .newBuilder()
            .setCurrency("USD")
            .setExchange("Nasdaq")
            .setSymbol("AAPL")
            .setHigh(high)
            .setLow(low)
            .setBid(Double.parseDouble(f.format(bid)))
            .setOffer(Double.parseDouble(f.format(offer)))
            .setLastUpdated(System.currentTimeMillis())
            .build();

        LOG.info("Creating Stock Quote End");
        kafkaProducer.send(kafkaConfigData.getTopicName(), 1234L, stockQuoteAvroModel);
        LOG.info("Sending Stock Quote to Kafka End");
        sleep(5000);
      }
    });
  }

  private void sleep(long sleepTimeMs) {
    try {
      Thread.sleep(sleepTimeMs);
    } catch (InterruptedException e) {
      throw new StockQuoteProducerException(
          "Error while sleeping for waiting stock quote data to create!!");
    }
  }

}
