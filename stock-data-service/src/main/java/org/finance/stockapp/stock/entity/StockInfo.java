package org.finance.stockapp.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "stock_info")
public class StockInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String symbol;
  private String exchange;
  private String currency;
  @OneToMany
  @JoinColumn(name = "stock_id", referencedColumnName = "id", insertable = false,
      updatable = false)
  private List<StockIntervalPrice> stockIntervalPriceList;

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

  public List<StockIntervalPrice> getStockIntervalPriceList() {
    return stockIntervalPriceList;
  }

  public void setStockIntervalPriceList(
      List<StockIntervalPrice> stockIntervalPriceList) {
    this.stockIntervalPriceList = stockIntervalPriceList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockInfo stockInfo = (StockInfo) o;
    return Objects.equals(id, stockInfo.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
