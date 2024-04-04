package org.message.queue.kafka.producer.service.impl;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import org.message.queue.kafka.model.avro.StockQuoteAvroModel;
import org.message.queue.kafka.producer.service.KafkaProducer;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import jakarta.annotation.PreDestroy;

@Service
public class StockQuoteKafkaProducerImpl implements KafkaProducer<Long, StockQuoteAvroModel> {

  private static final Logger LOG = LoggerFactory.getLogger(StockQuoteKafkaProducerImpl.class);

  private final KafkaTemplate<Long, StockQuoteAvroModel> kafkaTemplate;

  public StockQuoteKafkaProducerImpl(KafkaTemplate<Long, StockQuoteAvroModel> template) {
    this.kafkaTemplate = template;
  }

  @Override
  public void send(String topicName, Long key, StockQuoteAvroModel message) {
    LOG.info("Sending message='{}' to topic='{}'", message, topicName);
    CompletableFuture<SendResult<Long, StockQuoteAvroModel>> kafkaResultFuture = kafkaTemplate.send(
        topicName, key, message);
    kafkaResultFuture.whenComplete(getCallback(topicName, message));
  }

  @PreDestroy
  public void close() {
    if (kafkaTemplate != null) {
      LOG.info("Closing kafka producer!");
      kafkaTemplate.destroy();
    }
  }

  private BiConsumer<SendResult<Long, StockQuoteAvroModel>, Throwable> getCallback(String topicName,
      StockQuoteAvroModel message) {
    return (result, ex) -> {
      if (ex == null) {
        RecordMetadata metadata = result.getRecordMetadata();
        LOG.info(
            "Received new metadata. Topic@Offset: {}; Partition {}; Timestamp {}, at time {}",
            metadata,
            metadata.partition(),
            metadata.timestamp(),
            System.nanoTime());
      } else {
        LOG.error("Error while sending message {} to topic {}", message.toString(), topicName, ex);
      }
    };
  }
}
