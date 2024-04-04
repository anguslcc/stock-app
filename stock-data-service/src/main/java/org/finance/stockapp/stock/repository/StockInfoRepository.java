package org.finance.stockapp.stock.repository;

import java.util.Optional;
import org.finance.stockapp.stock.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockInfoRepository extends JpaRepository<StockInfoEntity, Integer> {

  @Query("SELECT s FROM StockInfoEntity s "
      + "WHERE s.symbol = :symbol "
      + "AND s.exchange = :exchange")
  Optional<StockInfoEntity> getBy(@Param("symbol") String symbol,
      @Param("exchange") String exchange);
}
