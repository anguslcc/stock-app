package org.finance.stock.external.payload;

import java.util.List;

public class MarketDataResponse {

  private MetaData meta;
  private List<PriceData> values;

  public MetaData getMeta() {
    return meta;
  }

  public void setMeta(MetaData meta) {
    this.meta = meta;
  }

  public List<PriceData> getValues() {
    return values;
  }

  public void setValues(List<PriceData> values) {
    this.values = values;
  }

  @Override
  public String toString() {
    return "MarketDataResponse{" +
        "meta=" + meta +
        ", values=" + values +
        '}';
  }
}
