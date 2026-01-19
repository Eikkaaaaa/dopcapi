package org.wolt.dopcapi3.order;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.wolt.dopcapi3.delivery.Delivery;

@Service
public class OrderService {

    public Order calculateOrder(JSONObject dynamic, Delivery delivery, int cartValue){

        int orderMinimumNoSurcharge = calculateSurcharge(dynamic, cartValue);
        int totalPrice = cartValue + orderMinimumNoSurcharge + delivery.fee();

        return new Order(totalPrice, orderMinimumNoSurcharge, cartValue, delivery);
    }

    private static int calculateSurcharge(JSONObject dynamic, int cartValue){

        return Math.max((fetchOrderMinimum(dynamic) - cartValue), 0);
    }

    private static int fetchOrderMinimum(JSONObject dynamic){
        return dynamic
                .getJSONObject("venue_raw")
                .getJSONObject("delivery_specs")
                .getInt("order_minimum_no_surcharge");
    }
}
