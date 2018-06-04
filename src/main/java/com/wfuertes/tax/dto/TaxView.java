package com.wfuertes.tax.dto;

import com.wfuertes.tax.model.Tax;
import com.wfuertes.tax.strategies.TaxCalculation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TaxView implements TaxCalculation {
    private String id;
    private BigDecimal value;
    private LocalDateTime createdAt;
    private String message;

    private TaxView() {
    }

    public String getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public BigDecimal calculate(BigDecimal value) {
        return this.value.multiply(value);
    }

    /**
     * Creates a TaxView.Builder
     *
     * @param tax
     * @return TaxView.Builder
     */
    public static Builder fromTax(Tax tax) {
        return new Builder().withId(tax.getId()).withValue(tax.getValue()).withCreatedAt(tax.getCreatedAt());
    }

    private static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private BigDecimal value;
        private LocalDateTime createdAt;
        private String message;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public TaxView build() {
            TaxView taxView = new TaxView();
            taxView.id = id;
            taxView.value = value;
            taxView.createdAt = createdAt;
            taxView.message = message;
            return taxView;
        }
    }
}
