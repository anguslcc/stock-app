package org.finance.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.finance", "org.message.queue"})
public class StockQuoteConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockQuoteConsumerApplication.class, args);
  }
}
