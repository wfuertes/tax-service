package com.wfuertes.tax.model;

import com.wfuertes.tax.strategies.TaxCalculation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "taxes")
public class Tax implements TaxCalculation {

    @Id
    private String id;
    private BigDecimal value;

    @Column(name = "created_at")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public BigDecimal calculate(BigDecimal value) {
        return this.value.multiply(value);
    }

    @Override
    public String toString() {
        return "Tax{" +
                "id='" + id + '\'' +
                ", value=" + value +
                '}';
    }
}
