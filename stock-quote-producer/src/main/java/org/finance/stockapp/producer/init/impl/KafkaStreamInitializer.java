package org.finance.stockapp.producer.init.impl;

import org.finance.config.kafka.KafkaConfigData;
import org.finance.stockapp.producer.init.StreamInitializer;
import org.message.queue.kafka.admin.KafkaAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamInitializer implements StreamInitializer {

  private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamInitializer.class);

  private final KafkaConfigData kafkaConfigData;

  private final KafkaAdminClient kafkaAdminClient;

  public KafkaStreamInitializer(KafkaConfigData configData, KafkaAdminClient adminClient) {
    this.kafkaConfigData = configData;
    this.kafkaAdminClient = adminClient;
  }

  @Override
  public void init() {
    kafkaAdminClient.createTopicList();
    kafkaAdminClient.checkSchemaRegistry();
    LOG.info("Topics with name {} is ready for operations!",
        kafkaConfigData.getTopicNamesToCreate().toArray());
  }
}