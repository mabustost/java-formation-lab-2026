package com.indra.retail;

import java.math.BigDecimal;

/** 15% de descuento para clientes con más de 12 meses de antigüedad. */
public final class LoyaltyDiscountStrategy extends PercentageDiscountStrategy {

    private static final int MINIMUM_MONTHS = 12;

    public LoyaltyDiscountStrategy() {
        super(new BigDecimal("15"));
    }

    @Override
    public DiscountType supports() {
        return DiscountType.LOYALTY;
    }

    @Override
    protected boolean isEligible(int customerMonths) {
        return customerMonths > MINIMUM_MONTHS;
    }
}
