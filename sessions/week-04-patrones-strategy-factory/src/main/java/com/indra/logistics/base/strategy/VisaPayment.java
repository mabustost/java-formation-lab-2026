package com.indra.logistics.base.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class VisaPayment extends CreditCardPayment {

    private static final BigDecimal TARIFF = new BigDecimal("0.035");

    public VisaPayment() {
        super(TARIFF, "Visa");
    }

    @Override
    public String methodCode() {
        return "VISA";
    }
}
