package com.indra.retail;

import java.math.BigDecimal;

public final class SeasonalDiscountStrategy extends PercentageDiscountStrategy {

    public SeasonalDiscountStrategy() {
        super(new BigDecimal("20"));
    }

    @Override
    public DiscountType supports() {
        return DiscountType.SEASONAL;
    }
}
