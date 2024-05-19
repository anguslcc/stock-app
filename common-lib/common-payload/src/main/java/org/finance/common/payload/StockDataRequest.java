package org.finance.common.payload;

import java.time.LocalDateTime;
import org.finance.common.enums.IntervalUnit;

public class StockDataRequest {

  private String symbol;
  private String interval;
  private String currency;
  private String exchange;
  private String type;
  private LocalDateTime endTime;
  private Double open;
  private Double high;
  private Double low;
  private Double close;
  private Integer volume;

  public String getSymbol() {
    return symbol;
  }

  public String getInterval() {
    return interval;
  }

  public String getCurrency() {
    return currency;
  }

  public String getExchange() {
    return exchange;
  }

  public String getType() {
    return type;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public Double getOpen() {
    return open;
  }

  public Double getHigh() {
    return high;
  }

  public Double getLow() {
    return low;
  }

  public Double getClose() {
    return close;
  }

  public Integer getVolume() {
    return volume;
  }

  public IntervalUnit getIntervalUnit() {
    if (this.interval != null) {
      String intervalUnitStr = this.interval.replaceAll("\\d", "");
      return IntervalUnit.convert(intervalUnitStr);
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    return "StockDataRequest{" +
        "symbol='" + symbol + '\'' +
        ", interval='" + interval + '\'' +
        ", currency='" + currency + '\'' +
        ", exchange='" + exchange + '\'' +
        ", type='" + type + '\'' +
        ", endTime=" + endTime +
        ", open=" + open +
        ", high=" + high +
        ", low=" + low +
        ", close=" + close +
        ", volume=" + volume +
        '}';
  }

  public String toKeyString() {
    return String.format("KeyInfo{%s|%s|%s}", symbol, exchange, endTime);
  }

  public Integer getIntervalValue() {
    if (this.interval != null) {
      String intervalValueStr = this.interval.replaceAll("[^\\d]", "");
      return Integer.valueOf(intervalValueStr);
    } else {
      return null;
    }
  }


  public static StockDataRequestBuilder newBuilder() {
    return new StockDataRequestBuilder();
  }

  public static final class StockDataRequestBuilder {

    private String symbol;
    private String interval;
    private String currency;
    private String exchange;
    private String type;
    private LocalDateTime endTime;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Integer volume;

    private StockDataRequestBuilder() {
    }

    public StockDataRequestBuilder setSymbol(String symbol) {
      this.symbol = symbol;
      return this;
    }

    public StockDataRequestBuilder setInterval(String interval) {
      this.interval = interval;
      return this;
    }

    public StockDataRequestBuilder setCurrency(String currency) {
      this.currency = currency;
      return this;
    }

    public StockDataRequestBuilder setExchange(String exchange) {
      this.exchange = exchange;
      return this;
    }

    public StockDataRequestBuilder setType(String type) {
      this.type = type;
      return this;
    }

    public StockDataRequestBuilder setEndTime(LocalDateTime endTime) {
      this.endTime = endTime;
      return this;
    }

    public StockDataRequestBuilder setOpen(Double open) {
      this.open = open;
      return this;
    }

    public StockDataRequestBuilder setHigh(Double high) {
      this.high = high;
      return this;
    }

    public StockDataRequestBuilder setLow(Double low) {
      this.low = low;
      return this;
    }

    public StockDataRequestBuilder setClose(Double close) {
      this.close = close;
      return this;
    }

    public StockDataRequestBuilder setVolume(Integer volume) {
      this.volume = volume;
      return this;
    }

    public StockDataRequest build() {
      StockDataRequest stockDataRequest = new StockDataRequest();
      stockDataRequest.exchange = this.exchange;
      stockDataRequest.high = this.high;
      stockDataRequest.close = this.close;
      stockDataRequest.volume = this.volume;
      stockDataRequest.endTime = this.endTime;
      stockDataRequest.type = this.type;
      stockDataRequest.symbol = this.symbol;
      stockDataRequest.interval = this.interval;
      stockDataRequest.currency = this.currency;
      stockDataRequest.low = this.low;
      stockDataRequest.open = this.open;
      return stockDataRequest;
    }
  }
}
