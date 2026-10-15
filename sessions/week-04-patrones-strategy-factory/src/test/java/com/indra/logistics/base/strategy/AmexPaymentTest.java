package com.indra.logistics.base.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class AmexPaymentTest {

    private final PaymentStrategy strategy = new AmexPayment();

    @Test
    void calculateFee_AMEX_Success() {
        BigDecimal amount = new BigDecimal("200.00");

        assertEquals("AMEX", strategy.methodCode());
        assertEquals(new BigDecimal("6.00"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("206.00"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago con tarjeta de crédito American Express procesado, se aplica comisión bancaria.", strategy.confirmationMessage(amount));
    }

    @Test
    void calculateFee_AmountBelow100_NoFee() {
        BigDecimal amount = new BigDecimal("99.99");

        assertEquals(new BigDecimal("0.00"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("99.99"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago con tarjeta de crédito American Express procesado, monto no aplica comisión bancaria.", strategy.confirmationMessage(amount));
    }
}
