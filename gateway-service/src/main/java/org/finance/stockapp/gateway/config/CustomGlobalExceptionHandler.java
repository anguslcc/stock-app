package org.finance.stockapp.gateway.config;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
@Order(Integer.MIN_VALUE)
public class CustomGlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

  public CustomGlobalExceptionHandler(final ErrorAttributes errorAttributes,
      final ApplicationContext applicationContext,
      final ServerCodecConfigurer configurer) {
    super(errorAttributes, new WebProperties.Resources(), applicationContext);
    setMessageReaders(configurer.getReaders());
    setMessageWriters(configurer.getWriters());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
    ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE);
    Map<String, Object> errorPropertiesMap = getErrorAttributes(request, options);

    LOG.error("errorPropertiesMap {}", errorPropertiesMap);

    Throwable throwable = getError(request);
    HttpStatusCode httpStatus = determineHttpStatus(throwable);

    return ServerResponse.status(httpStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .headers(httpHeaders ->
            httpHeaders.add("X-ADVICE", errorPropertiesMap.get("message").toString())
        )
        .build();
  }

  private HttpStatusCode determineHttpStatus(Throwable throwable) {

    return (throwable instanceof ResponseStatusException rse) ? rse.getStatusCode()
        : HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
