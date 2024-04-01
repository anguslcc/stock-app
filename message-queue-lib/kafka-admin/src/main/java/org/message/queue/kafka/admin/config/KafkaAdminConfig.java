package org.message.queue.kafka.admin.config;

import java.util.Map;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.finance.config.kafka.KafkaConfigData;
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
