package org.finance.stockapp.stock.controller;

import org.finance.common.payload.StockDataRequest;
import org.finance.stockapp.stock.entity.StockInfo;
import org.finance.stockapp.stock.service.StockDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
public class StockDataController {

  private final StockDataService stockDataService;

  public StockDataController(StockDataService stockDataService) {
    this.stockDataService = stockDataService;
  }

  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("Test Called Successfully");
  }

  @GetMapping("/save-stock-data")
  public ResponseEntity<StockInfo> saveStockData() {
    StockDataRequest stockDataRequest = StockDataRequest
        .newBuilder()
        .setSymbol("AAPL")
        .setCurrency("USD")
        .setExchange("NASDAQ")
        .setInterval("1min")
        .setEndTime(LocalDateTime.now())
        .setLow(5.67)
        .setHigh(8.12)
        .setOpen(5.8)
        .setClose(7.1)
        .setVolume(3001)
        .build();

    return ResponseEntity.ok(stockDataService.saveStockData(stockDataRequest));
  }
}
