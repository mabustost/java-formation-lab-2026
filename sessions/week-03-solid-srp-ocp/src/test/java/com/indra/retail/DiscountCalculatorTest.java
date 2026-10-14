package com.indra.retail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountCalculatorTest {

    private static final BigDecimal PRICE = new BigDecimal("100.00");

    private final DiscountCalculator calculator = new DiscountCalculator(List.of(
            new StandardDiscountStrategy(),
            new SeasonalDiscountStrategy(),
            new LoyaltyDiscountStrategy()));

    @Test
    @DisplayName("Debe aplicar 5% con descuento STANDARD")
    void shouldApplyStandardDiscount() {
        assertEquals(0, new BigDecimal("95.00").compareTo(calculator.apply(PRICE, DiscountType.STANDARD)));
    }

    @Test
    @DisplayName("Debe aplicar 20% con descuento SEASONAL")
    void shouldApplySeasonalDiscount() {
        assertEquals(0, new BigDecimal("80.00").compareTo(calculator.apply(PRICE, DiscountType.SEASONAL)));
    }

    @Test
    @DisplayName("Debe aplicar LOYALTY solo a clientes con más de 12 meses")
    void shouldApplyLoyaltyDiscountOnlyAfterTwelveMonths() {
        assertEquals(0, new BigDecimal("85.00").compareTo(calculator.apply(PRICE, DiscountType.LOYALTY, 13)));
        assertEquals(0, new BigDecimal("100.00").compareTo(calculator.apply(PRICE, DiscountType.LOYALTY, 12)));
    }

    @Test
    @DisplayName("Debe aceptar una estrategia nueva sin modificar DiscountCalculator (OCP)")
    void shouldUseAnyInjectedStrategyWithoutModifyingCalculator() {
        DiscountStrategy halfPrice = new DiscountStrategy() {
            @Override
            public DiscountType supports() {
                return DiscountType.SEASONAL;
            }

            @Override
            public BigDecimal apply(BigDecimal price, int customerMonths) {
                return price.divide(new BigDecimal("2"));
            }
        };
        DiscountCalculator customCalculator = new DiscountCalculator(List.of(halfPrice));

        assertEquals(0, new BigDecimal("50.00").compareTo(customCalculator.apply(PRICE, DiscountType.SEASONAL)));
    }

    @Test
    @DisplayName("Debe fallar si no hay estrategia registrada para el tipo")
    void shouldThrowWhenTypeHasNoStrategy() {
        DiscountCalculator onlyStandard = new DiscountCalculator(List.of(new StandardDiscountStrategy()));

        assertThrows(IllegalArgumentException.class, () -> onlyStandard.apply(PRICE, DiscountType.LOYALTY));
    }

    @Test
    @DisplayName("Debe rechazar dos estrategias para el mismo tipo")
    void shouldRejectDuplicateStrategies() {
        assertThrows(IllegalArgumentException.class, () -> new DiscountCalculator(List.of(
                new StandardDiscountStrategy(), new StandardDiscountStrategy())));
    }

    @Test
    @DisplayName("Debe rechazar precio o tipo nulos")
    void shouldRejectNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> calculator.apply(null, DiscountType.STANDARD));
        assertThrows(IllegalArgumentException.class, () -> calculator.apply(PRICE, null));
    }
}
