package com.indra.logistics.base.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class AmexPayment extends CreditCardPayment {

    private static final BigDecimal TARIFF = new BigDecimal("0.03");

    public AmexPayment() {
        super(TARIFF, "American Express");
    }

    @Override
    public String methodCode() {
        return "AMEX";
    }
}
