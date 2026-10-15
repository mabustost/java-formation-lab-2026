package com.indra.logistics.base.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CashPaymentTest {

    private final PaymentStrategy strategy = new CashPayment();

    @Test
    void calculateFee_CASH_Success() {
        BigDecimal amount = new BigDecimal("100.00");

        assertEquals("CASH", strategy.methodCode());
        assertEquals(new BigDecimal("0.00"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("100.00"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago en efectivo registrado, sin comisión.", strategy.confirmationMessage(amount));
    }
}
