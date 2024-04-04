package org.finance.stockapp.stock.entity;

import jakarta.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class StockIntervalPriceId implements Serializable {

  @Column(name = "stock_id")
  private Integer stockId;
  @Column(name = "end_time")
  private LocalDateTime endTime;

  @Column(name = "interval_id")
  private Integer intervalId;

  public StockIntervalPriceId() {
  }

  public StockIntervalPriceId(Integer stockId, LocalDateTime endTime, Integer intervalId) {
    this.stockId = stockId;
    this.endTime = endTime;
    this.intervalId = intervalId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockIntervalPriceId that = (StockIntervalPriceId) o;
    return Objects.equals(stockId, that.stockId) && Objects.equals(endTime,
        that.endTime) && Objects.equals(intervalId, that.intervalId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stockId, endTime, intervalId);
  }
}
