package com.indra.logistics.base.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PaypalPaymentTest {

    private final PaymentStrategy strategy = new PaypalPayment();

    @Test
    void calculateFee_PAYPAL_Success() {
        BigDecimal amount = new BigDecimal("100.00");

        assertEquals("PAYPAL", strategy.methodCode());
        assertEquals(new BigDecimal("2.00"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("102.00"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago con PayPal procesado, comisión de plataforma aplicada.", strategy.confirmationMessage(amount));
    }
}
