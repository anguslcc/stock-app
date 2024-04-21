package org.finance.stockapp.stock.controller;

import java.util.List;
import org.finance.common.payload.StockDataRequest;
import org.finance.common.payload.StockDetailResponse;
import org.finance.common.payload.StockMetaResponse;
import org.finance.stockapp.stock.service.StockDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @GetMapping(value = "/stocks/",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyAuthority('READ-ROLE')")
  public ResponseEntity<List<StockMetaResponse>> getStockInfo() {
    return ResponseEntity.ok(stockDataService.getStockMetaList());
  }


  @GetMapping(value = "/stocks/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyAuthority('READ-ROLE')")
  public ResponseEntity<StockDetailResponse> getStockInfo(@PathVariable Integer id) {
    return ResponseEntity.ok(stockDataService.getStockInfo(id));
  }

  @PostMapping(value = "/stocks",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyAuthority('FULL-CONTROL-ROLE')")
  public ResponseEntity<Void> saveStockData(@RequestBody StockDataRequest stockDataRequest) {
    stockDataService.saveStockData(stockDataRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
