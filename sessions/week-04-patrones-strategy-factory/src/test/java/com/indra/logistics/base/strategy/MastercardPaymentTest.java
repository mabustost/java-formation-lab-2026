package com.indra.logistics.base.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class MastercardPaymentTest {

    private final PaymentStrategy strategy = new MastercardPayment();

    @Test
    void calculateFee_MASTERCARD_Success() {
        BigDecimal amount = new BigDecimal("100.00");

        assertEquals("MASTERCARD", strategy.methodCode());
        assertEquals(new BigDecimal("3.00"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("103.00"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago con tarjeta de crédito Mastercard procesado, se aplica comisión bancaria.", strategy.confirmationMessage(amount));
    }

    @Test
    void calculateFee_AmountBelow100_NoFee() {
        BigDecimal amount = new BigDecimal("99.99");

        assertEquals(new BigDecimal("0.00"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("99.99"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago con tarjeta de crédito Mastercard procesado, monto no aplica comisión bancaria.", strategy.confirmationMessage(amount));
    }
}
