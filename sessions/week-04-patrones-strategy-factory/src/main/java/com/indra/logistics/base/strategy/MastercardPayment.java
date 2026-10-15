package com.indra.logistics.base.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class MastercardPayment extends CreditCardPayment {

    private static final BigDecimal TARIFF = new BigDecimal("0.03");

    public MastercardPayment() {
        super(TARIFF, "Mastercard");
    }

    @Override
    public String methodCode() {
        return "MASTERCARD";
    }
}
