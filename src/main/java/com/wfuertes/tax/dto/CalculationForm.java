package com.wfuertes.tax.dto;

import java.math.BigDecimal;

public class CalculationForm {

    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
