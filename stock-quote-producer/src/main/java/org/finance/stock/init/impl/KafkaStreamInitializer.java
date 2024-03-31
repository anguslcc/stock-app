package org.finance.stock.init.impl;

import org.finance.common.config.kafka.KafkaConfigData;
import org.message.queue.kafka.admin.KafkaAdminClient;
import org.finance.stock.init.StreamInitializer;
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
    kafkaAdminClient.createTopics();
    kafkaAdminClient.checkSchemaRegistry();
    LOG.info("Topics with name {} is ready for operations!",
        kafkaConfigData.getTopicNamesToCreate().toArray());
  }
}