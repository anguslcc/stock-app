package org.finance.stockapp.consumer.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import org.finance.common.payload.StockDataRequest;
import org.finance.config.kafka.KafkaConfigData;
import org.finance.config.kafka.KafkaConsumerConfigData;
import org.finance.stockapp.consumer.service.KafkaConsumer;
import org.finance.stockapp.consumer.service.StockDataRestService;
import org.message.queue.kafka.admin.KafkaAdminClient;
import org.message.queue.kafka.model.avro.StockQuoteAvroModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class StockQuoteConsumer implements KafkaConsumer<StockQuoteAvroModel> {

  private static final Logger LOG = LoggerFactory.getLogger(StockQuoteConsumer.class);
  private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
  private final KafkaAdminClient kafkaAdminClient;
  private final KafkaConfigData kafkaConfigData;
  private final KafkaConsumerConfigData kafkaConsumerConfigData;

  private final StockDataRestService stockDataRestService;

  public StockQuoteConsumer(KafkaListenerEndpointRegistry listenerEndpointRegistry,
      KafkaAdminClient adminClient,
      KafkaConfigData configData,
      KafkaConsumerConfigData consumerConfigData,
      StockDataRestService stockDataRestService) {
    this.kafkaListenerEndpointRegistry = listenerEndpointRegistry;
    this.kafkaAdminClient = adminClient;
    this.kafkaConfigData = configData;
    this.kafkaConsumerConfigData = consumerConfigData;
    this.stockDataRestService = stockDataRestService;
  }

  @EventListener
  public void onAppStarted(ApplicationStartedEvent event) {
    kafkaAdminClient.checkTopicListCreated();
    LOG.info("Topics with name {} is ready for operations!",
        kafkaConfigData.getTopicNamesToCreate().toArray());
    Objects.requireNonNull(kafkaListenerEndpointRegistry
        .getListenerContainer(kafkaConsumerConfigData.getConsumerGroupId())).start();
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}", topics = "${kafka-config.topic-name}")
  public void receive(@Payload List<StockQuoteAvroModel> messageList,
      @Header(KafkaHeaders.RECEIVED_KEY) List<Long> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitionList,
      @Header(KafkaHeaders.OFFSET) List<Long> offsetList) {
    LOG.info("{} number of message received with keys {}, partitions {} and offsets {}, " +
            "sending it to elastic: Thread id {}",
        messageList.size(),
        keys,
        partitionList,
        offsetList,
        Thread.currentThread().threadId());

    for (StockQuoteAvroModel message : messageList) {
      LOG.info(
          "Symbol: {}, Interval: {}, Currency: {}, High: {}, Low: {}, Open: {}, Close: {}, Volume: {}, DateTime: {} ",
          message.getSymbol(),
          message.getInterval(),
          message.getCurrency(),
          message.getHigh(),
          message.getLow(),
          message.getOpen(),
          message.getClose(),
          message.getVolume(),
          LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getDatetime()),
              TimeZone.getDefault().toZoneId()));

      stockDataRestService.saveStockData(buildStockDataRequest(message));
    }

  }

  private StockDataRequest buildStockDataRequest(StockQuoteAvroModel message) {
    return StockDataRequest
        .newBuilder()
        .setSymbol(message.getSymbol())
        .setCurrency(message.getCurrency())
        .setExchange(message.getExchange())
        .setInterval(message.getInterval())
        .setEndTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getDatetime()),
            TimeZone.getDefault().toZoneId()))
        .setLow(message.getLow())
        .setHigh(message.getHigh())
        .setOpen(message.getOpen())
        .setClose(message.getClose())
        .setVolume(message.getVolume())
        .build();
  }

}
