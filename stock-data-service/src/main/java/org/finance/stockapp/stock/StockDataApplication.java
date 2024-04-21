package org.finance.stockapp.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.finance")
public class StockDataApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockDataApplication.class, args);
  }

}
