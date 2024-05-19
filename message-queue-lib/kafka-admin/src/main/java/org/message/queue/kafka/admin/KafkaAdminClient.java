package org.message.queue.kafka.admin;

import java.util.Optional;
import org.message.queue.kafka.admin.exception.KafkaClientException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.finance.config.kafka.KafkaConfigData;
import org.finance.config.retry.RetryConfigData;
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
  private final RetryConfigData retryConfigData;
  private final AdminClient adminClient;
  private final RetryTemplate retryTemplate;
  private final WebClient webClient;

  public KafkaAdminClient(KafkaConfigData config,
      RetryConfigData retryConfigData,
      AdminClient client,
      RetryTemplate template,
      @Qualifier("defaultWebClient") WebClient webClient) {
    this.kafkaConfigData = config;
    this.retryConfigData = retryConfigData;
    this.adminClient = client;
    this.retryTemplate = template;
    this.webClient = webClient;
  }

  public void createTopics() {
    CreateTopicsResult createTopicsResult;
    try {
      createTopicsResult = retryTemplate.execute(this::doCreateTopics);
      LOG.info("Create topic result {}", createTopicsResult.values().values());
    } catch (Exception e) {
      throw new KafkaClientException("Reached max number of retry for creating kafka topic(s)!", e);
    }
    checkTopicsCreated();
  }

  public void checkTopicsCreated() {
    LOG.info("Check if topics have been created.");
    Collection<TopicListing> topics = getTopics();
    int retryCount = 1;
    Integer maxRetry = retryConfigData.getMaxAttempts();
    int multiplier = retryConfigData.getMultiplier().intValue();
    Long sleepTimeMs = retryConfigData.getSleepTimeMs();
    for (String topic : kafkaConfigData.getTopicNamesToCreate()) {
      while (!isTopicCreated(topics, topic)) {
        checkMaxRetry(retryCount++, maxRetry);
        sleep(sleepTimeMs);
        sleepTimeMs *= multiplier;
        topics = getTopics();
      }
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

  private void sleep(Long sleepTimeMs) {
    try {
      Thread.sleep(sleepTimeMs);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new KafkaClientException("Error while sleeping for waiting new created topics!!");
    }
  }

  private void checkMaxRetry(int retry, Integer maxRetry) {
    if (retry > maxRetry) {
      throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)!");
    }
  }

  private boolean isTopicCreated(Collection<TopicListing> topics, String topicName) {
    if (topics == null) {
      return false;
    }
    return topics.stream().anyMatch(topic -> topic.name().equals(topicName));
  }

  private CreateTopicsResult doCreateTopics(RetryContext retryContext) {
    List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
    LOG.info("Creating {} topics(s), Retry count: {}", topicNames.size(), retryContext.getRetryCount());
    List<NewTopic> kafkaTopics = topicNames.stream().map(topic -> new NewTopic(
        topic.trim(),
        kafkaConfigData.getNumOfPartitions(),
        kafkaConfigData.getReplicationFactor()
    )).toList();
    return adminClient.createTopics(kafkaTopics);
  }

  private Collection<TopicListing> getTopics() {
    Collection<TopicListing> topics;
    try {
      topics = retryTemplate.execute(this::doGetTopics);
    } catch (Exception e) {
      throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)!", e);
    }
    return topics;
  }

  private Collection<TopicListing> doGetTopics(RetryContext retryContext)
      throws ExecutionException, InterruptedException {
    LOG.info("Reading kafka topic {}, Retry count: {}",
        kafkaConfigData.getTopicNamesToCreate().toArray(), retryContext.getRetryCount());
    Collection<TopicListing> topics = adminClient.listTopics().listings().get();
    if (topics != null) {
      topics.forEach(topic -> LOG.debug("Topic with name {}", topic.name()));
    }
    return topics;
  }

}
