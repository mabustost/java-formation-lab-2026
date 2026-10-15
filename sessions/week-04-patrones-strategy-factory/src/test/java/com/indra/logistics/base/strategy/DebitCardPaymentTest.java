package com.indra.logistics.base.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class DebitCardPaymentTest {

    private final PaymentStrategy strategy = new DebitCardPayment();

    @Test
    void calculateFee_DEBIT_CARD_Success() {
        BigDecimal amount = new BigDecimal("100.00");

        assertEquals("DEBIT_CARD", strategy.methodCode());
        assertEquals(new BigDecimal("4.00"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("104.00"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago con tarjeta de débito procesado, se aplica comisión bancaria.", strategy.confirmationMessage(amount));
    }
}
