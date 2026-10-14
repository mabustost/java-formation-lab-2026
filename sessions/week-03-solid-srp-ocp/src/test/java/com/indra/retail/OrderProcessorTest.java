package com.indra.retail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderProcessorTest {

    /** Doble de prueba que registra las notificaciones en lugar de enviarlas. */
    private static class RecordingNotifier extends OrderNotifier {
        private final List<String> sent = new ArrayList<>();

        @Override
        public String notifyCustomer(String customerEmail, String message) {
            String notification = super.notifyCustomer(customerEmail, message);
            sent.add(notification);
            return notification;
        }
    }

    private final RecordingNotifier notifier = new RecordingNotifier();

    private final OrderProcessor processor = new OrderProcessor(
            new StockValidator(),
            notifier,
            new DiscountCalculator(List.of(
                    new StandardDiscountStrategy(),
                    new SeasonalDiscountStrategy(),
                    new LoyaltyDiscountStrategy())));

    @Test
    @DisplayName("Debe delegar el descuento y notificar al cliente con el total")
    void shouldDelegateDiscountAndNotifyCustomer() {
        Order order = new Order("P-1", new BigDecimal("200.00"), DiscountType.LOYALTY, 2, "cliente@indra.com", 24);

        BigDecimal total = processor.process(order, 10);

        assertEquals(0, new BigDecimal("170.00").compareTo(total));
        assertEquals(1, notifier.sent.size());
        assertTrue(notifier.sent.get(0).contains("P-1"));
        assertTrue(notifier.sent.get(0).contains("170.00"));
    }

    @Test
    @DisplayName("Debe rechazar el pedido sin stock y no notificar")
    void shouldRejectOrderWithoutStockAndNotNotify() {
        Order order = new Order("P-2", new BigDecimal("100.00"), DiscountType.STANDARD, 5, "cliente@indra.com");

        assertThrows(IllegalStateException.class, () -> processor.process(order, 2));
        assertTrue(notifier.sent.isEmpty());
    }

    @Test
    @DisplayName("Debe exigir todas las dependencias por constructor")
    void shouldRequireAllDependencies() {
        assertThrows(NullPointerException.class,
                () -> new OrderProcessor(new StockValidator(), new OrderNotifier(), null));
    }
}
