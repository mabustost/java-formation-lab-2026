package com.indra.logistics.base.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.indra.logistics.base.UnknownPaymentMethodException;
import com.indra.logistics.base.strategy.AmexPayment;
import com.indra.logistics.base.strategy.BankTransferPayment;
import com.indra.logistics.base.strategy.CashPayment;
import com.indra.logistics.base.strategy.DebitCardPayment;
import com.indra.logistics.base.strategy.MastercardPayment;
import com.indra.logistics.base.strategy.PaypalPayment;
import com.indra.logistics.base.strategy.VisaPayment;

class PaymentStrategyFactoryImplTest {

    private final PaymentStrategyFactoryImpl factory = new PaymentStrategyFactoryImpl(List.of(
            new CashPayment(), new VisaPayment(), new MastercardPayment(), new AmexPayment(),
            new DebitCardPayment(), new BankTransferPayment(), new PaypalPayment()));

    @Test
    void getStrategy_AllSevenMethods_ReturnMatchingStrategy() {
        assertInstanceOf(CashPayment.class, factory.getStrategy("CASH"));
        assertInstanceOf(VisaPayment.class, factory.getStrategy("VISA"));
        assertInstanceOf(MastercardPayment.class, factory.getStrategy("MASTERCARD"));
        assertInstanceOf(AmexPayment.class, factory.getStrategy("AMEX"));
        assertInstanceOf(DebitCardPayment.class, factory.getStrategy("DEBIT_CARD"));
        assertInstanceOf(BankTransferPayment.class, factory.getStrategy("BANK_TRANSFER"));
        assertInstanceOf(PaypalPayment.class, factory.getStrategy("PAYPAL"));
    }

    @Test
    void getStrategy_UnknownMethod_ThrowsUnknownPaymentMethodException() {
        UnknownPaymentMethodException ex = assertThrows(UnknownPaymentMethodException.class,
                () -> factory.getStrategy("CRYPTO"));

        assertEquals("método de pago 'CRYPTO' no soportado", ex.getMessage());
    }

    @Test
    void getStrategy_NullMethod_ThrowsUnknownPaymentMethodException() {
        assertThrows(UnknownPaymentMethodException.class, () -> factory.getStrategy(null));
    }

    @Test
    void constructor_DuplicateMethodCode_Fails() {
        assertThrows(IllegalStateException.class,
                () -> new PaymentStrategyFactoryImpl(List.of(new CashPayment(), new CashPayment())));
    }
}
