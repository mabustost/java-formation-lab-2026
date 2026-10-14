package com.indra.retail;

import java.math.BigDecimal;

/**
 * Punto de extensión (OCP): para agregar un nuevo descuento se crea una nueva
 * implementación y se registra en {@link DiscountCalculator}; no se modifica
 * ninguna estrategia ni el calculador.
 */
public interface DiscountStrategy {

    DiscountType supports();

    BigDecimal apply(BigDecimal price, int customerMonths);
}
