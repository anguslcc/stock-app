package org.finance.stockapp.stock.exception;

import java.text.MessageFormat;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String pattern, Object... arguments) {
    super(MessageFormat.format(pattern, arguments));
  }
}
