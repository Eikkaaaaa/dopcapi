package org.wolt.dopcapi3;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wolt.dopcapi3.order.Order;
import org.wolt.dopcapi3.util.ErrorResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1")
public class RequestController {
    
    private final RequestService requestService;
    
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }
    
    @GetMapping("/delivery-order-price")
    public ResponseEntity<?> deliveryOrderPrice(
            @RequestParam String venue_slug,
            @RequestParam int cart_value,
            @RequestParam double user_lat,
            @RequestParam double user_lon
    ) throws URISyntaxException, IOException {
        
        Order order = requestService.orderPrice(venue_slug, cart_value, user_lat, user_lon);
        if (order == null) return ResponseEntity.badRequest().body(new ErrorResponse("Distance is too far away"));
        return ResponseEntity.ok(order);
    }
}
