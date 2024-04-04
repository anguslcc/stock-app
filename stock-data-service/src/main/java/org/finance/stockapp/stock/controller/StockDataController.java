package org.finance.stockapp.stock.controller;

import org.finance.common.payload.StockDataRequest;
import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.finance.stockapp.stock.service.StockDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
public class StockDataController {

  private final StockDataService stockDataService;

  public StockDataController(StockDataService stockDataService) {
    this.stockDataService = stockDataService;
  }

  @GetMapping("/stocks/{id}")
  public ResponseEntity<StockInfoEntity> getStockInfo(@PathVariable Integer id) {
    return ResponseEntity.ok(stockDataService.getStockInfo(id));
  }

  @PostMapping("/stocks")
  public ResponseEntity<Void> saveStockData(@RequestBody StockDataRequest stockDataRequest) {
    stockDataService.saveStockData(stockDataRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/save-stock-data")
  public ResponseEntity<Void> testSaveStockData() {
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

    stockDataService.saveStockData(stockDataRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
