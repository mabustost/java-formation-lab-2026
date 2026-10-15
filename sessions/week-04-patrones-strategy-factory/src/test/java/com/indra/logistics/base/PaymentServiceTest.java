package com.indra.logistics.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.indra.logistics.base.factory.PaymentStrategyFactory;
import com.indra.logistics.base.factory.PaymentStrategyFactoryImpl;
import com.indra.logistics.base.strategy.PaymentStrategy;
import com.indra.logistics.base.strategy.VisaPayment;

class PaymentServiceTest {

    @Test
    void process_DelegatesToStrategyAndBuildsResult() {
        PaymentService service = new PaymentService(new PaymentStrategyFactoryImpl(List.of(new VisaPayment())));

        PaymentResult result = service.process(new PaymentRequest(new BigDecimal("200.00"), "VISA"));

        assertEquals(new PaymentResult("VISA", new BigDecimal("200.00"), new BigDecimal("7.00"),
                new BigDecimal("207.00"),
                "Pago con tarjeta de crédito Visa procesado, se aplica comisión bancaria."), result);
    }

    @Test
    void process_NewStrategy_WorksWithoutModifyingService() {
        PaymentStrategy giftCard = new PaymentStrategy() {
            @Override
            public String methodCode() {
                return "GIFT_CARD";
            }

            @Override
            public BigDecimal calculateFee(BigDecimal amount) {
                return new BigDecimal("1.00");
            }

            @Override
            public String confirmationMessage(BigDecimal amount) {
                return "Pago con tarjeta de regalo.";
            }
        };
        PaymentStrategyFactory factory = code -> giftCard;

        PaymentResult result = new PaymentService(factory).process(new PaymentRequest(new BigDecimal("50.00"), "GIFT_CARD"));

        assertEquals(new BigDecimal("51.00"), result.total());
        assertEquals("GIFT_CARD", result.method());
    }

    @Test
    void process_UnknownMethod_PropagatesException() {
        PaymentService service = new PaymentService(new PaymentStrategyFactoryImpl(List.of(new VisaPayment())));

        assertThrows(UnknownPaymentMethodException.class,
                () -> service.process(new PaymentRequest(new BigDecimal("10.00"), "CRYPTO")));
    }
}
