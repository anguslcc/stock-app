package org.finance.stock.external.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class PriceData {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime datetime;
  private Double open;
  private Double high;
  private Double low;
  private Double close;
  private Integer volume;

  public LocalDateTime getDatetime() {
    return datetime;
  }

  public void setDatetime(LocalDateTime datetime) {
    this.datetime = datetime;
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

  @Override
  public String toString() {
    return "PriceData{" +
        "datetime=" + datetime +
        ", open='" + open + '\'' +
        ", high='" + high + '\'' +
        ", low='" + low + '\'' +
        ", close='" + close + '\'' +
        ", volume='" + volume + '\'' +
        '}';
  }
}
