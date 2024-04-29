package org.finance.stockapp.stock.exception;

import java.text.MessageFormat;

public class AlreadyExistsException extends RuntimeException {

  public AlreadyExistsException(String pattern, Object... arguments) {
    super(MessageFormat.format(pattern, arguments));
  }
}
