package org.finance.messagequeue.kafka.config;

import java.util.Map;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaAdminConfig {

  private final KafkaConfigData kafkaConfigData;

  public KafkaAdminConfig(KafkaConfigData configData) {
    this.kafkaConfigData = configData;
  }

  @Bean
  public AdminClient adminClient() {
    return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
        kafkaConfigData.getBootstrapServers()));
  }
}
