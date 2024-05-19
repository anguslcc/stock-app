package org.finance.stockapp.producer.external.payload;

import java.util.List;
import org.finance.stockapp.producer.external.payload.enums.Status;

public class MarketDataResponse {

  private MetaData meta;
  private List<PriceData> values;

  private String message;
  private Integer code;
  private Status status;

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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "MarketDataResponse{" +
        "meta=" + meta +
        ", values=" + values +
        ", message='" + message + '\'' +
        ", code=" + code +
        ", status=" + status +
        '}';
  }
}
