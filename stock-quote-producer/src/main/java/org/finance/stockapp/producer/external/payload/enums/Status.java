package org.finance.stockapp.producer.external.payload.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
  OK("ok"), ERROR("error");
  private final String value;

  Status(String value) {
    this.value = value;
  }

  @JsonValue
  @Override
  public String toString() {
    return this.value;
  }
}
