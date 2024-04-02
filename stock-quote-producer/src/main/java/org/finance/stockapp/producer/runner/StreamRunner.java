package org.finance.stockapp.producer.runner;

import org.finance.stockapp.producer.exception.StockQuoteProducerException;

public interface StreamRunner {

  void start() throws StockQuoteProducerException;
}
