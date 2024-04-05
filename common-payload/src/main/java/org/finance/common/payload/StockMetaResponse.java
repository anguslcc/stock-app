package org.finance.common.payload;

public class StockMetaResponse {

  private Integer id;
  private String symbol;
  private String exchange;
  private String currency;

  public Integer getId() {
    return id;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getExchange() {
    return exchange;
  }

  public String getCurrency() {
    return currency;
  }

  public static StockMetaResponseBuilder newBuilder() {
    return new StockMetaResponseBuilder();
  }

  public static final class StockMetaResponseBuilder {

    private Integer id;
    private String symbol;
    private String exchange;
    private String currency;

    private StockMetaResponseBuilder() {
    }

    public StockMetaResponseBuilder setId(Integer id) {
      this.id = id;
      return this;
    }

    public StockMetaResponseBuilder setSymbol(String symbol) {
      this.symbol = symbol;
      return this;
    }

    public StockMetaResponseBuilder setExchange(String exchange) {
      this.exchange = exchange;
      return this;
    }

    public StockMetaResponseBuilder setCurrency(String currency) {
      this.currency = currency;
      return this;
    }

    public StockMetaResponse build() {
      StockMetaResponse stockMetaResponse = new StockMetaResponse();
      stockMetaResponse.symbol = this.symbol;
      stockMetaResponse.currency = this.currency;
      stockMetaResponse.id = this.id;
      stockMetaResponse.exchange = this.exchange;
      return stockMetaResponse;
    }
  }
}
