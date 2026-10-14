package com.indra.retail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Base común para descuentos porcentuales. Centraliza el cálculo y el redondeo
 * para que cada estrategia concreta solo declare su tipo, su porcentaje y,
 * si aplica, su condición de elegibilidad (patrón Template Method).
 */
public abstract class PercentageDiscountStrategy implements DiscountStrategy {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final int MONEY_SCALE = 2;

    private final BigDecimal priceFactor;

    protected PercentageDiscountStrategy(BigDecimal discountPercentage) {
        Objects.requireNonNull(discountPercentage, "discountPercentage es obligatorio");
        if (discountPercentage.signum() < 0 || discountPercentage.compareTo(ONE_HUNDRED) > 0) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100: " + discountPercentage);
        }
        this.priceFactor = BigDecimal.ONE.subtract(discountPercentage.divide(ONE_HUNDRED));
    }

    @Override
    public final BigDecimal apply(BigDecimal price, int customerMonths) {
        if (!isEligible(customerMonths)) {
            return price;
        }
        return price.multiply(priceFactor).setScale(MONEY_SCALE, RoundingMode.HALF_EVEN);
    }

    /** Por defecto el descuento aplica a todos los clientes. */
    protected boolean isEligible(int customerMonths) {
        return true;
    }
}
