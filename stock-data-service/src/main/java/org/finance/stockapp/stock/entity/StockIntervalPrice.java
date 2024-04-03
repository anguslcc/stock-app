package org.finance.stockapp.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_interval_price")
@IdClass(StockIntervalPriceId.class)
public class StockIntervalPrice {

  @Id
  private Integer stockId;
  @Id
  private LocalDateTime endTime;
  @Id
  private Integer intervalId;
  private Double open;
  private Double high;
  private Double low;
  private Double close;
  private Integer volume;

  @OneToOne
  @JoinColumn(name = "interval_id", referencedColumnName = "id", insertable = false,
      updatable = false)
  private Interval interval;

  public Integer getStockId() {
    return stockId;
  }

  public void setStockId(Integer stockId) {
    this.stockId = stockId;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public Integer getIntervalId() {
    return intervalId;
  }

  public void setIntervalId(Integer intervalId) {
    this.intervalId = intervalId;
  }

  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  public Double getHigh() {
    return high;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  public Double getLow() {
    return low;
  }

  public void setLow(Double low) {
    this.low = low;
  }

  public Double getClose() {
    return close;
  }

  public void setClose(Double close) {
    this.close = close;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  public Interval getInterval() {
    return interval;
  }

  public void setInterval(Interval interval) {
    this.interval = interval;
  }
}
