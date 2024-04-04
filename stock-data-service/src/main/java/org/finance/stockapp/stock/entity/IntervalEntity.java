package org.finance.stockapp.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Objects;
import org.finance.common.enums.IntervalUnit;


@Entity
@Table(name = "interval", uniqueConstraints = {@UniqueConstraint(columnNames = {"value", "unit"})})
public class IntervalEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Integer value;
  @Enumerated(EnumType.STRING)
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
    IntervalEntity that = (IntervalEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "IntervalEntity{" +
        "id=" + id +
        ", value=" + value +
        ", unit=" + unit +
        '}';
  }
}
