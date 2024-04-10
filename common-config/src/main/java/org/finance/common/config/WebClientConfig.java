package org.finance.common.config;

import org.finance.config.microservice.MicroserviceConfigData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Bean(name = "defaultWebClient")
  WebClient defaultWebClient() {
    return WebClient.builder().build();
  }

  @Bean(name = "stockAppWebClient")
  WebClient stockAppWebClient(MicroserviceConfigData microserviceConfigData) {
    return WebClient.builder()
        .baseUrl(microserviceConfigData.getBaseUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

}
