package org.finance.common.config;

import org.finance.config.microservice.MicroserviceConfigData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Bean(name = "defaultWebClient")
  WebClient defaultWebClient() {
    return WebClient.builder().build();
  }

  @Bean(name = "stockAppWebClient")
  @ConditionalOnProperty(name = "stockapp-web-client.enabled", havingValue = "true")
  WebClient stockAppWebClient(ReactiveOAuth2AuthorizedClientManager authorizedClientManager,
      MicroserviceConfigData microserviceConfigData) {
    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2Filter =
        new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);

    oauth2Filter.setDefaultClientRegistrationId("stockapp");

    return WebClient.builder()
        .baseUrl(microserviceConfigData.getBaseUrl())
        .filter(oauth2Filter)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  @Bean
  @ConditionalOnProperty(name = "stockapp-web-client.enabled", havingValue = "true")
  ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
      ReactiveClientRegistrationRepository clientRegistrations,
      ReactiveOAuth2AuthorizedClientService clientService) {
    AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
        new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations,
            clientService);
    authorizedClientManager.setAuthorizedClientProvider(
        ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials()
            .build()
    );
    return authorizedClientManager;
  }

  @Bean
  @ConditionalOnProperty(name = "stockapp-web-client.enabled", havingValue = "true")
  public ReactiveOAuth2AuthorizedClientService clientService(
      ReactiveClientRegistrationRepository clientRegistrations) {
    return new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
  }

}
