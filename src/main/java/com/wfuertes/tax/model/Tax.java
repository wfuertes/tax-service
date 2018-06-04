package com.wfuertes.tax.model;

import com.wfuertes.tax.dto.TaxForm;
import com.wfuertes.tax.strategies.TaxCalculation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Tax implements TaxCalculation {

    private String id;
    private BigDecimal value;
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

    public static Builder fromForm(TaxForm form) {
        return new Builder()
                .withId(form.getId())
                .withValue(form.getValue());
    }

    public static class Builder {
        private String id;
        private BigDecimal value;
        private LocalDateTime createdAt;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder withCreateAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Tax build() {
            Tax newTax = new Tax();
            newTax.setId(id);
            newTax.setValue(value);
            newTax.setCreatedAt(createdAt);
            return newTax;
        }
    }
}
