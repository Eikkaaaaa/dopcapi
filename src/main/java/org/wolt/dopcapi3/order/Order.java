package org.wolt.dopcapi3.order;

import org.wolt.dopcapi3.delivery.Delivery;

public record Order(
        int total_price,
        int small_order_surcharge,
        int cart_value,
        Delivery delivery
) {
}
