package com.wfuertes.tax.strategies;

import java.math.BigDecimal;

public interface TaxCalculation {

    default BigDecimal calculate(BigDecimal value) {
        return value;
    }
}
