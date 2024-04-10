package org.finance.common.payload;

public class StockDetailResponse {

  private StockMetaResponse meta;

  private StockIntervalData latest;
  private StockIntervalData aggregated;

  public StockMetaResponse getMeta() {
    return meta;
  }

  public void setMeta(StockMetaResponse meta) {
    this.meta = meta;
  }

  public StockIntervalData getLatest() {
    return latest;
  }

  public void setLatest(StockIntervalData latest) {
    this.latest = latest;
  }

  public StockIntervalData getAggregated() {
    return aggregated;
  }

  public void setAggregated(StockIntervalData aggregated) {
    this.aggregated = aggregated;
  }

  @Override
  public String toString() {
    return "StockDetailResponse{" +
        "meta=" + meta +
        ", latest=" + latest +
        ", aggregated=" + aggregated +
        '}';
  }
}
