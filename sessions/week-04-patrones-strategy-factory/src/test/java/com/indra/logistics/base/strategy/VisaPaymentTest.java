package com.indra.logistics.base.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class VisaPaymentTest {

    private final PaymentStrategy strategy = new VisaPayment();

    @Test
    void calculateFee_VISA_Success() {
        BigDecimal amount = new BigDecimal("200.00");

        assertEquals("VISA", strategy.methodCode());
        assertEquals(new BigDecimal("7.00"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("207.00"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago con tarjeta de crédito Visa procesado, se aplica comisión bancaria.", strategy.confirmationMessage(amount));
    }

    @Test
    void calculateFee_AmountBelow100_NoFee() {
        BigDecimal amount = new BigDecimal("99.99");

        assertEquals(new BigDecimal("0.00"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("99.99"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago con tarjeta de crédito Visa procesado, monto no aplica comisión bancaria.", strategy.confirmationMessage(amount));
    }
}
