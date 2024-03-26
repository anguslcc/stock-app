package org.finance.stock.exception;

public class StockQuoteProducerException extends RuntimeException {

  public StockQuoteProducerException() {
    super();
  }

  public StockQuoteProducerException(String message) {
    super(message);
  }

  public StockQuoteProducerException(String message, Throwable cause) {
    super(message, cause);
  }
}
