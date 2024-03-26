package org.finance.stock.runner;

import org.finance.stock.exception.StockQuoteProducerException;

public interface StreamRunner {

  void start() throws StockQuoteProducerException;
}
