package org.finance.stockapp.stock.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class StockDataController {

  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("Test Called Successfully");
  }
}
