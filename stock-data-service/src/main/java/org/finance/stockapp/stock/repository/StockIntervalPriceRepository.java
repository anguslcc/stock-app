package org.finance.stockapp.stock.repository;

import org.finance.stockapp.stock.entity.StockIntervalPrice;
import org.finance.stockapp.stock.entity.StockIntervalPriceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockIntervalPriceRepository extends
    JpaRepository<StockIntervalPrice, StockIntervalPriceId> {

}
