package org.finance.common.payload;

import java.time.LocalDateTime;

public class StockIntervalData {

  private Double open;
  private Double close;
  private Double high;
  private Double low;
  private Integer volume;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  public Double getOpen() {
    return open;
  }

  public Double getClose() {
    return close;
  }

  public Double getHigh() {
    return high;
  }

  public Double getLow() {
    return low;
  }

  public Integer getVolume() {
    return volume;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  @Override
  public String toString() {
    return "StockIntervalData{" +
        "open=" + open +
        ", close=" + close +
        ", high=" + high +
        ", low=" + low +
        ", volume=" + volume +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        '}';
  }

  public static StockIntervalDataBuilder newBuilder() {
    return new StockIntervalDataBuilder();
  }

  public static final class StockIntervalDataBuilder {

    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Integer volume;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private StockIntervalDataBuilder() {
    }

    public StockIntervalDataBuilder setOpen(Double open) {
      this.open = open;
      return this;
    }

    public StockIntervalDataBuilder setClose(Double close) {
      this.close = close;
      return this;
    }

    public StockIntervalDataBuilder setHigh(Double high) {
      this.high = high;
      return this;
    }

    public StockIntervalDataBuilder setLow(Double low) {
      this.low = low;
      return this;
    }

    public StockIntervalDataBuilder setVolume(Integer volume) {
      this.volume = volume;
      return this;
    }

    public StockIntervalDataBuilder setStartTime(LocalDateTime startTime) {
      this.startTime = startTime;
      return this;
    }

    public StockIntervalDataBuilder setEndTime(LocalDateTime endTime) {
      this.endTime = endTime;
      return this;
    }

    public StockIntervalData build() {
      StockIntervalData stockIntervalData = new StockIntervalData();
      stockIntervalData.startTime = this.startTime;
      stockIntervalData.low = this.low;
      stockIntervalData.high = this.high;
      stockIntervalData.open = this.open;
      stockIntervalData.volume = this.volume;
      stockIntervalData.endTime = this.endTime;
      stockIntervalData.close = this.close;
      return stockIntervalData;
    }
  }
}
