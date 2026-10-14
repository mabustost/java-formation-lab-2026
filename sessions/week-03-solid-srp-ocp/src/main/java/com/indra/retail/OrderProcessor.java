package com.indra.retail;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Orquestador: valida stock, delega el descuento y notifica.
 * No contiene reglas de negocio propias; todas sus dependencias se inyectan por constructor.
 */
public class OrderProcessor {

    private final StockValidator stockValidator;
    private final OrderNotifier orderNotifier;
    private final DiscountCalculator discountCalculator;

    public OrderProcessor(StockValidator stockValidator, OrderNotifier orderNotifier,
                          DiscountCalculator discountCalculator) {
        this.stockValidator = Objects.requireNonNull(stockValidator, "stockValidator es obligatorio");
        this.orderNotifier = Objects.requireNonNull(orderNotifier, "orderNotifier es obligatorio");
        this.discountCalculator = Objects.requireNonNull(discountCalculator, "discountCalculator es obligatorio");
    }

    public BigDecimal process(Order order, int availableStock) {
        if (!stockValidator.hasEnoughStock(availableStock, order.getRequestedQuantity())) {
            throw new IllegalStateException("Stock insuficiente para el pedido " + order.getId());
        }

        BigDecimal finalPrice = discountCalculator.apply(
                order.getPrice(), order.getDiscountType(), order.getCustomerMonths());

        orderNotifier.notifyCustomer(order.getCustomerEmail(),
                "Tu pedido " + order.getId() + " fue procesado. Total: " + finalPrice);

        return finalPrice;
    }
}
