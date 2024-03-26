package org.finance.stock;

import org.finance.stock.init.StreamInitializer;
import org.finance.stock.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.finance")
public class StockQuoteProducerApplication implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(StockQuoteProducerApplication.class);

  private final StreamRunner streamRunner;

  private final StreamInitializer streamInitializer;

  public StockQuoteProducerApplication(StreamRunner streamRunner,
      StreamInitializer streamInitializer) {
    this.streamRunner = streamRunner;
    this.streamInitializer = streamInitializer;
  }

  public static void main(String[] args) {
    SpringApplication.run(StockQuoteProducerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    LOG.info("App starts...");
    streamInitializer.init();
    streamRunner.start();

  }
}
