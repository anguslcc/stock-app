package org.finance.stockapp.stock.repository;

import java.util.Optional;
import org.finance.common.enums.IntervalUnit;
import org.finance.stockapp.stock.entity.IntervalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IntervalRepository extends JpaRepository<IntervalEntity, Integer> {

  @Query("SELECT i FROM IntervalEntity i "
      + "WHERE i.value = :value "
      + "AND i.unit = :unit")
  Optional<IntervalEntity> getBy(@Param("value") Integer value,
      @Param("unit") IntervalUnit unit);
}
