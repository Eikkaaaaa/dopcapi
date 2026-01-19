package org.wolt.dopcapi3;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.wolt.dopcapi3.delivery.DeliveryService;
import org.wolt.dopcapi3.order.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RequestService {
    
    private JSONObject staticJson;
    private JSONObject dynamicJson;
    private final DeliveryService deliveryService;
    private final OrderService orderService;
    
    public RequestService(DeliveryService deliveryService, OrderService orderService) {
        this.deliveryService = deliveryService;
        this.orderService = orderService;
    }
    
    public String orderPrice(String venue_slug, int cart_value, double user_lat, double user_lon) throws URISyntaxException, IOException {
        
        fetchStatic(venue_slug);
        fetchDynamic(venue_slug);
        
        deliveryService.calculateDelivery(staticJson, dynamicJson,  user_lat, user_lon);
        
        return "venue_slug: " + venue_slug +
                "\ncart_value: " + cart_value +
                "\nuser_lat: " + user_lat +
                "\nuser_lon: " + user_lon;
    }
    
    private void fetchStatic(String venue_slug) throws URISyntaxException, IOException {
        String address = "https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/" + venue_slug + "/static";
        this.staticJson = new JSONObject(connect(address));
    }
    
    private void fetchDynamic(String venue_slug) throws URISyntaxException, IOException {
        String address = "https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/" + venue_slug + "/dynamic";
        this.dynamicJson = new JSONObject(connect(address));
    }
    
    private static String connect(String address) throws URISyntaxException, IOException {
        URI url = new URI(address);
        HttpURLConnection conn = (HttpURLConnection) url.toURL().openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        StringBuilder response = new StringBuilder();
        
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        conn.disconnect();
        
        return response.toString();
    }
}
