package org.finance.stock.consumer.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import org.finance.config.kafka.KafkaConfigData;
import org.finance.config.kafka.KafkaConsumerConfigData;
import org.finance.stock.consumer.KafkaConsumer;
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

  public StockQuoteConsumer(KafkaListenerEndpointRegistry listenerEndpointRegistry,
      KafkaAdminClient adminClient,
      KafkaConfigData configData,
      KafkaConsumerConfigData consumerConfigData) {
    this.kafkaListenerEndpointRegistry = listenerEndpointRegistry;
    this.kafkaAdminClient = adminClient;
    this.kafkaConfigData = configData;
    this.kafkaConsumerConfigData = consumerConfigData;
  }

  @EventListener
  public void onAppStarted(ApplicationStartedEvent event) {
    kafkaAdminClient.checkTopicsCreated();
    LOG.info("Topics with name {} is ready for operations!",
        kafkaConfigData.getTopicNamesToCreate().toArray());
    Objects.requireNonNull(kafkaListenerEndpointRegistry
        .getListenerContainer(kafkaConsumerConfigData.getConsumerGroupId())).start();
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}", topics = "${kafka-config.topic-name}")
  public void receive(@Payload List<StockQuoteAvroModel> messages,
      @Header(KafkaHeaders.RECEIVED_KEY) List<Long> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    LOG.info("{} number of message received with keys {}, partitions {} and offsets {}, " +
            "sending it to elastic: Thread id {}",
        messages.size(),
        keys.toString(),
        partitions.toString(),
        offsets.toString(),
        Thread.currentThread().getId());

    for (StockQuoteAvroModel message : messages) {
      LOG.info(
          "Symbol: {}, Currency: {}, High: {}, Low: {}, Open: {}, Close: {}, Volume: {}, DateTime: {} ",
          message.getSymbol(),
          message.getCurrency(),
          message.getHigh(),
          message.getLow(),
          message.getOpen(),
          message.getClose(),
          message.getVolume(),
          LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getDatetime()),
              TimeZone.getDefault().toZoneId()));
    }

  }
}
