package org.finance.stockapp.stock.entity.enums;

public enum IntervalUnit {
  MINUTE, HOUR, DAY, WEEK, MONTH;

  public static IntervalUnit convert(String intervalUnitStr) {
    return switch (intervalUnitStr) {
      case "min" -> MINUTE;
      case "h" -> HOUR;
      case "day" -> DAY;
      case "week" -> WEEK;
      case "month" -> MONTH;
      default -> null;
    };
  }
}
