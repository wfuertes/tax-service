package com.wfuertes.tax.dto;

import java.math.BigDecimal;

public class CalculationView extends BasicResponse {

    private String taxName;
    private BigDecimal taxValue;
    private BigDecimal value;

    public CalculationView(int status, String message) {
        super(status, message);
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
