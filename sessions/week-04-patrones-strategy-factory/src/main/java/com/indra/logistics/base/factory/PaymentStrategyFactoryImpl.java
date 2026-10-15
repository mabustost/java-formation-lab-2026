package com.indra.logistics.base.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.indra.logistics.base.UnknownPaymentMethodException;
import com.indra.logistics.base.strategy.PaymentStrategy;

/**
 * Factory con autodescubrimiento: Spring inyecta todas las clases {@code @Component}
 * que implementan {@link PaymentStrategy}. Para agregar un método de pago basta con
 * crear una nueva estrategia; no se modifica esta clase ni {@code PaymentService}.
 */
@Component
public class PaymentStrategyFactoryImpl implements PaymentStrategyFactory {

    private final Map<String, PaymentStrategy> strategiesByCode;

    public PaymentStrategyFactoryImpl(List<PaymentStrategy> strategies) {
        Objects.requireNonNull(strategies, "strategies es obligatorio");
        Map<String, PaymentStrategy> byCode = new HashMap<>();
        for (PaymentStrategy strategy : strategies) {
            PaymentStrategy previous = byCode.put(strategy.methodCode(), strategy);
            if (previous != null) {
                throw new IllegalStateException("Método de pago duplicado: " + strategy.methodCode());
            }
        }
        this.strategiesByCode = Map.copyOf(byCode);
    }

    public List<String> registeredMethodCodes() {
        return strategiesByCode.keySet().stream().sorted().toList();
    }

    @Override
    public PaymentStrategy getStrategy(String methodCode) {
        PaymentStrategy strategy = methodCode == null ? null : strategiesByCode.get(methodCode);
        if (strategy == null) {
            throw new UnknownPaymentMethodException(methodCode);
        }
        return strategy;
    }
}
