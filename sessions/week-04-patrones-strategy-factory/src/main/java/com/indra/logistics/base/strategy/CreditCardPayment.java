package com.indra.logistics.base.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Base común para tarjetas de crédito: los montos menores a 100 no pagan comisión
 * y los demás pagan la tarifa de la franquicia. Evita duplicar esta regla en
 * Visa, Mastercard y American Express.
 */
public abstract class CreditCardPayment implements PaymentStrategy {

    private static final BigDecimal MINIMUM_AMOUNT_FOR_FEE = new BigDecimal("100");
    private static final BigDecimal NO_FEE = BigDecimal.ZERO.setScale(2);

    private final BigDecimal tariff;
    private final String brandName;

    protected CreditCardPayment(BigDecimal tariff, String brandName) {
        this.tariff = Objects.requireNonNull(tariff, "tariff es obligatorio");
        this.brandName = Objects.requireNonNull(brandName, "brandName es obligatorio");
    }

    @Override
    public final BigDecimal calculateFee(BigDecimal amount) {
        if (!appliesFee(amount)) {
            return NO_FEE;
        }
        return amount.multiply(tariff).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public final String confirmationMessage(BigDecimal amount) {
        String prefix = "Pago con tarjeta de crédito " + brandName + " procesado, ";
        return appliesFee(amount)
                ? prefix + "se aplica comisión bancaria."
                : prefix + "monto no aplica comisión bancaria.";
    }

    private boolean appliesFee(BigDecimal amount) {
        return amount.compareTo(MINIMUM_AMOUNT_FOR_FEE) >= 0;
    }
}
