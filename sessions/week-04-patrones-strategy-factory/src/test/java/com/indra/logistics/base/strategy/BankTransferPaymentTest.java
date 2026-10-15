package com.indra.logistics.base.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class BankTransferPaymentTest {

    private final PaymentStrategy strategy = new BankTransferPayment();

    @Test
    void calculateFee_BANK_TRANSFER_Success() {
        BigDecimal amount = new BigDecimal("300.00");

        assertEquals("BANK_TRANSFER", strategy.methodCode());
        assertEquals(new BigDecimal("7.50"), strategy.calculateFee(amount));
        assertEquals(new BigDecimal("307.50"), amount.add(strategy.calculateFee(amount)));
        assertEquals("Pago por transferencia bancaria registrado, comisión bancaria aplicada.", strategy.confirmationMessage(amount));
    }
}
