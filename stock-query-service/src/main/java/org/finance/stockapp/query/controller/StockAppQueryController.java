package org.finance.stockapp.query.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class StockAppQueryController {

  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("Test Called Successfully");
  }
}
