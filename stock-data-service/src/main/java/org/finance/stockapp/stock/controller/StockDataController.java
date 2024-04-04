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

}
