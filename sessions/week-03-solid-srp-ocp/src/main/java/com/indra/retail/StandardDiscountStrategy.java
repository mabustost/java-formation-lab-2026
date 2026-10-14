package com.indra.retail;

import java.math.BigDecimal;

public final class StandardDiscountStrategy extends PercentageDiscountStrategy {

    public StandardDiscountStrategy() {
        super(new BigDecimal("5"));
    }

    @Override
    public DiscountType supports() {
        return DiscountType.STANDARD;
    }
}
