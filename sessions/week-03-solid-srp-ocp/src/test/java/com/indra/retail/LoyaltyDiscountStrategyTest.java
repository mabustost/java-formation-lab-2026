package com.indra.retail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoyaltyDiscountStrategyTest {

    private final LoyaltyDiscountStrategy strategy = new LoyaltyDiscountStrategy();

    @Test
    @DisplayName("Debe aplicar 15% de descuento a clientes con más de 12 meses")
    void shouldApplyFifteenPercentDiscountAfterTwelveMonths() {
        BigDecimal result = strategy.apply(new BigDecimal("100"), 13);

        assertEquals(0, new BigDecimal("85").compareTo(result));
    }

    @Test
    @DisplayName("No debe aplicar descuento a clientes con 12 meses o menos")
    void shouldNotDiscountCustomersWithTwelveMonthsOrLess() {
        BigDecimal result = strategy.apply(new BigDecimal("100"), 12);

        assertEquals(0, new BigDecimal("100").compareTo(result));
    }
}
