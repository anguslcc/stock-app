package org.finance.stockapp.producer.transformer;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.TimeZone;
import org.finance.stockapp.producer.external.payload.MarketDataResponse;
import org.finance.stockapp.producer.external.payload.MetaData;
import org.finance.stockapp.producer.external.payload.PriceData;
import org.message.queue.kafka.model.avro.StockQuoteAvroModel;

public class MarketDataToAvroTransformer {

  private MarketDataToAvroTransformer() {

  }

  public static List<StockQuoteAvroModel> from(MarketDataResponse marketDataResponse) {
    List<StockQuoteAvroModel> stockQuoteAvroModelList = new ArrayList<>();
    List<PriceData> priceDataList = marketDataResponse.getValues();
    MetaData metaData = marketDataResponse.getMeta();
    for (PriceData priceData : priceDataList) {
      stockQuoteAvroModelList.add(StockQuoteAvroModel
          .newBuilder()
          .setSymbol(metaData.getSymbol())
          .setExchange(metaData.getExchange())
          .setCurrency(metaData.getCurrency())
          .setLow(priceData.getLow())
          .setHigh(priceData.getHigh())
          .setVolume(priceData.getVolume())
          .setOpen(priceData.getOpen())
          .setClose(priceData.getClose())
          .setDatetime(toMillis(priceData.getDatetime()))
          .build()
      );
    }

    return stockQuoteAvroModelList;
  }

  private static long toMillis(LocalDateTime localDateTime) {
    return localDateTime.atZone(TimeZone.getDefault().toZoneId()).toInstant()
        .toEpochMilli();
  }
}
