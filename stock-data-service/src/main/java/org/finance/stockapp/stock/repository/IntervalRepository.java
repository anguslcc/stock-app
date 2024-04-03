package org.finance.stockapp.stock.repository;

import org.finance.stockapp.stock.entity.Interval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntervalRepository extends JpaRepository<Interval, Integer> {

}
