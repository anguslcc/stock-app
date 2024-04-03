package org.finance.stockapp.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import org.finance.stockapp.stock.entity.enums.IntervalUnit;

@Entity
@Table(name = "interval")
public class Interval {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Integer value;
  private IntervalUnit unit;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public IntervalUnit getUnit() {
    return unit;
  }

  public void setUnit(IntervalUnit unit) {
    this.unit = unit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Interval interval = (Interval) o;
    return Objects.equals(id, interval.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
