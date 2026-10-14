package com.indra.retail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentageDiscountStrategyTest {

    private static PercentageDiscountStrategy withPercentage(String percentage) {
        return new PercentageDiscountStrategy(new BigDecimal(percentage)) {
            @Override
            public DiscountType supports() {
                return DiscountType.STANDARD;
            }
        };
    }

    @Test
    @DisplayName("Debe redondear el resultado a 2 decimales")
    void shouldRoundToTwoDecimals() {
        BigDecimal result = withPercentage("15").apply(new BigDecimal("19.99"), 0);

        assertEquals(new BigDecimal("16.99"), result);
    }

    @Test
    @DisplayName("Debe rechazar porcentajes fuera del rango 0-100")
    void shouldRejectInvalidPercentage() {
        assertThrows(IllegalArgumentException.class, () -> withPercentage("-1"));
        assertThrows(IllegalArgumentException.class, () -> withPercentage("101"));
    }
}
