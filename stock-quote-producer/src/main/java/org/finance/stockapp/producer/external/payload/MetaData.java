package org.finance.stockapp.producer.external.payload;

public class MetaData {

  private String symbol;
  private String interval;
  private String currency;
  private String exchange;
  private String type;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getInterval() {
    return interval;
  }

  public void setInterval(String interval) {
    this.interval = interval;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "MetaData{" +
        "symbol='" + symbol + '\'' +
        ", interval='" + interval + '\'' +
        ", currency='" + currency + '\'' +
        ", exchange='" + exchange + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
