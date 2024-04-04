package org.finance.stockapp.stock.repository;

import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockInfoRepository extends JpaRepository<StockInfoEntity, Integer> {

}
