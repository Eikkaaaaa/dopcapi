package org.wolt.dopcapi3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String deliveryOrderPrice(
            @RequestParam String venue_slug,
            @RequestParam int cart_value,
            @RequestParam double user_lat,
            @RequestParam double user_lon
    ) throws URISyntaxException, IOException {
        
        return requestService.orderPrice(venue_slug, cart_value, user_lat, user_lon);
    }
}
