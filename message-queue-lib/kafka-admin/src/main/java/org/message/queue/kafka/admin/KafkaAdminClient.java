package org.message.queue.kafka.admin;

import java.util.Optional;
import org.message.queue.kafka.admin.exception.KafkaClientException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.finance.config.kafka.KafkaConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import reactor.core.publisher.Mono;

@Component
@EnableRetry
public class KafkaAdminClient {

  private static final Logger LOG = LoggerFactory.getLogger(KafkaAdminClient.class);

  private final KafkaConfigData kafkaConfigData;
  private final AdminClient adminClient;
  private final RetryTemplate retryTemplate;
  private final WebClient webClient;

  public KafkaAdminClient(KafkaConfigData config,
      AdminClient client,
      RetryTemplate template,
      @Qualifier("defaultWebClient") WebClient webClient) {
    this.kafkaConfigData = config;
    this.adminClient = client;
    this.retryTemplate = template;
    this.webClient = webClient;
  }

  public void createTopicList() {
    CreateTopicsResult createTopicsResult;
    try {
      createTopicsResult = retryTemplate.execute(this::doCreateTopicList);
      LOG.info("Create topic result {}", createTopicsResult.values().values());
    } catch (Exception e) {
      throw new KafkaClientException("Reached max number of retry for creating kafka topic(s)!", e);
    }
    checkTopicListCreated();
  }

  public void checkTopicListCreated() {
    LOG.info("Check if topics have been created.");
    try {
      boolean isCreated = retryTemplate.execute(
          retryContext -> checkTopicCreated(retryContext, kafkaConfigData.getTopicName()));
      LOG.info("Is Topic {} created: {}", kafkaConfigData.getTopicName(), isCreated);

    } catch (Exception e) {
      throw new KafkaClientException("Reached max number of retry check topics", e);
    }
  }

  public void checkSchemaRegistry() {
    LOG.info("Check Schema Registry if it is ready.");
    try {
      boolean isReady = retryTemplate.execute(this::isSchemaRegistryReady);
      LOG.info("Is Schema Registry ready: {}", isReady);
    } catch (Exception e) {
      throw new KafkaClientException("Reached max number of retry Schema Registry", e);
    }
  }

  private boolean isSchemaRegistryReady(RetryContext retryContext) {
    Optional<Boolean> result = webClient
        .method(HttpMethod.GET)
        .uri(kafkaConfigData.getSchemaRegistryUrl())
        .exchangeToMono(response -> {
          if (response.statusCode().is2xxSuccessful()) {
            return Mono.just(true);
          } else {
            LOG.info("Schema Registry is not ready, HTTP status code: {}, Retry Count: {}",
                response.statusCode().value(), retryContext.getRetryCount());
            return Mono.error(new KafkaClientException("Schema Registry is not ready"));
          }
        })
        .doOnError(e -> LOG.error("Error connecting to Schema Registry: {}", e.getMessage()))
        .blockOptional();

    return result.orElse(false);
  }

  private boolean checkTopicCreated(RetryContext retryContext, String topicName) {
    Collection<TopicListing> topicList = getTopicList();
    if (topicList == null || topicList.stream()
        .noneMatch(topic -> topic.name().equals(topicName))) {
      LOG.info("Waiting for creating Topic {} in Kafka, Retry Count: {}", topicName,
          retryContext.getRetryCount());
      throw new KafkaClientException(String.format("Topic %s not found in Kafka", topicName));
    }

    return true;
  }

  private CreateTopicsResult doCreateTopicList(RetryContext retryContext) {
    List<String> topicNameList = kafkaConfigData.getTopicNamesToCreate();
    LOG.info("Creating {} topics(s), Retry count: {}", topicNameList.size(),
        retryContext.getRetryCount());
    List<NewTopic> kafkaTopicList = topicNameList.stream().map(topic -> new NewTopic(
        topic.trim(),
        kafkaConfigData.getNumOfPartitions(),
        kafkaConfigData.getReplicationFactor()
    )).toList();
    return adminClient.createTopics(kafkaTopicList);
  }

  private Collection<TopicListing> getTopicList() {
    Collection<TopicListing> topicList;
    try {
      topicList = retryTemplate.execute(this::doGetTopicList);
    } catch (Exception e) {
      throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)!", e);
    }
    return topicList;
  }

  private Collection<TopicListing> doGetTopicList(RetryContext retryContext)
      throws ExecutionException, InterruptedException {
    LOG.info("Reading kafka topic {}, Retry count: {}",
        kafkaConfigData.getTopicNamesToCreate().toArray(), retryContext.getRetryCount());
    Collection<TopicListing> topicList = adminClient.listTopics().listings().get();
    if (topicList != null) {
      topicList.forEach(topic -> LOG.info("Topic with name {}", topic.name()));
    }
    return topicList;
  }

}
