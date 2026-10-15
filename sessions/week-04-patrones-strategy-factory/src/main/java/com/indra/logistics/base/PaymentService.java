package com.indra.logistics.base;

import java.math.BigDecimal;

import com.indra.logistics.base.factory.PaymentStrategyFactory;
import com.indra.logistics.base.strategy.PaymentStrategy;

/**
 * Orquesta el cobro sin conocer ningún método de pago concreto: la factory entrega
 * la estrategia y esta calcula la comisión y el mensaje. Agregar un método de pago
 * no requiere modificar esta clase.
 */
public class PaymentService {

    private final PaymentStrategyFactory factory;

    public PaymentService(PaymentStrategyFactory factory) {
        this.factory = factory;
    }

    public PaymentResult process(PaymentRequest request) {
        PaymentStrategy strategy = factory.getStrategy(request.method());
        BigDecimal amount = request.amount();
        BigDecimal fee = strategy.calculateFee(amount);
        BigDecimal total = amount.add(fee);
        return new PaymentResult(strategy.methodCode(), amount, fee, total, strategy.confirmationMessage(amount));
    }
}
