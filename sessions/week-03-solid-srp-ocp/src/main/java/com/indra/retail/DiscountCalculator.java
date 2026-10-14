package com.indra.retail;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Única responsabilidad (SRP): resolver qué estrategia de descuento aplica a un tipo.
 * Las estrategias llegan por constructor, así que un nuevo descuento no requiere
 * modificar esta clase (OCP).
 */
public class DiscountCalculator {

    private final Map<DiscountType, DiscountStrategy> strategies;

    public DiscountCalculator(Collection<? extends DiscountStrategy> strategies) {
        Objects.requireNonNull(strategies, "strategies es obligatorio");
        this.strategies = new EnumMap<>(DiscountType.class);
        for (DiscountStrategy strategy : strategies) {
            Objects.requireNonNull(strategy, "strategy no puede ser nula");
            DiscountStrategy previous = this.strategies.put(strategy.supports(), strategy);
            if (previous != null) {
                throw new IllegalArgumentException("Estrategia duplicada: " + strategy.supports());
            }
        }
    }

    public BigDecimal apply(BigDecimal price, DiscountType type) {
        return apply(price, type, 0);
    }

    public BigDecimal apply(BigDecimal price, DiscountType type, int customerMonths) {
        if (price == null || type == null) {
            throw new IllegalArgumentException("price y type son obligatorios");
        }
        DiscountStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No existe estrategia para: " + type);
        }
        return strategy.apply(price, customerMonths);
    }
}
