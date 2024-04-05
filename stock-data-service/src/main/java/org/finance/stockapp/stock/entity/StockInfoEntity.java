package org.finance.stockapp.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import java.util.Objects;
import org.finance.common.payload.StockMetaResponse;


@Entity
@Table(name = "stock_info", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"symbol", "exchange"})})
public class StockInfoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String symbol;
  private String exchange;
  private String currency;
  @OneToMany
  @JoinColumn(name = "stock_id", referencedColumnName = "id", insertable = false,
      updatable = false)
  private List<StockIntervalPriceEntity> stockIntervalPriceEntityList;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public List<StockIntervalPriceEntity> getStockIntervalPriceEntityList() {
    return stockIntervalPriceEntityList;
  }

  public void setStockIntervalPriceEntityList(
      List<StockIntervalPriceEntity> stockIntervalPriceEntityList) {
    this.stockIntervalPriceEntityList = stockIntervalPriceEntityList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockInfoEntity that = (StockInfoEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "StockInfoEntity{" +
        "id=" + id +
        ", symbol='" + symbol + '\'' +
        ", exchange='" + exchange + '\'' +
        ", currency='" + currency + '\'' +
        ", stockIntervalPriceEntityList=" + stockIntervalPriceEntityList +
        '}';
  }

  public StockMetaResponse toStockMetaResponse() {
    return StockMetaResponse.newBuilder()
        .setId(this.id)
        .setSymbol(this.symbol)
        .setExchange(this.exchange)
        .setCurrency(this.currency)
        .build();
  }
}
