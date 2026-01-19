package org.wolt.dopcapi3.delivery;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DeliveryService {
    
    private int distance;
    
    public void calculateDelivery(JSONObject coordinates, JSONObject dynamic, double user_lat, double user_lon){
    
        double lon = extractLocation(coordinates).getDouble(0);
        double lat = extractLocation(coordinates).getDouble(1);
        ArrayList<JSONObject> distance_ranges = fetchDistanceRanges(dynamic);
        int distance = calculateDistance(user_lat, user_lon, lat, lon);
        
        int deliveryConstant = calculateDeliveryConstant(distance, distance_ranges);
        int basePrice = calculateDeliveryBasePrice(dynamic);
        int deliveryMultiplier = calculateDeliveryMultiplier(distance, distance_ranges);
        
        IO.println("Delivery Fee: " + deliveryConstant);
        IO.println("Base Price: " + basePrice);
        IO.println("Delivery Multiplier: " + deliveryMultiplier);
        
        
        // IO.println("Lat: " + lat);
        // IO.println("Lon: " + lon);
        // IO.println("Distance ranges: " + distance_ranges);
        // IO.println("Distance: " + distance);
    }
    
    private static int calculateDeliveryConstant(int distance, ArrayList<JSONObject> distance_ranges){
        for (JSONObject distance_range : distance_ranges) {
            if (distance_range.getInt("max") == 0) return -1;
            if (distance_range.getInt("min") < distance && distance_range.getInt("max") > distance) {
                return distance_range.getInt("a");
            }
        }
        return -1;
    }
    
    private static int calculateDeliveryMultiplier(int distance, ArrayList<JSONObject> distance_ranges){
        for (JSONObject distance_range : distance_ranges) {
            if (distance_range.getInt("min") < distance && distance_range.getInt("max") > distance) {
                return distance_range.getInt("b");
            }
        }
        return 0;
    }
    
    private static int calculateDeliveryBasePrice(JSONObject jsonObject){
        return jsonObject
                .getJSONObject("venue_raw")
                .getJSONObject("delivery_specs")
                .getJSONObject("delivery_pricing")
                .getInt("base_price");
    }
    
    private int calculateDistance(double fromLat, double fromLon, double toLat, double toLon) {
        GlobalCoordinates source = new GlobalCoordinates(fromLat, fromLon);
        GlobalCoordinates destination = new GlobalCoordinates(toLat, toLon);
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, destination);
        int dist =  Math.toIntExact(Math.round(geoCurve.getEllipsoidalDistance()));
        this.distance = dist;
        return dist;
    }
    
    private static JSONArray extractLocation(JSONObject coordinates){
        return coordinates
                .getJSONObject("venue_raw")
                .getJSONObject("location")
                .getJSONArray("coordinates");
    }
    
    private static ArrayList<JSONObject> fetchDistanceRanges(JSONObject dynamic){
        
        ArrayList<JSONObject> final_distance_ranges = new ArrayList<>();
        JSONArray distance_ranges = dynamic
                .getJSONObject("venue_raw")
                .getJSONObject("delivery_specs")
                .getJSONObject("delivery_pricing")
                .getJSONArray("distance_ranges");
        
        for (int i = 0; i < distance_ranges.length(); i++) {
            final_distance_ranges.add(distance_ranges.getJSONObject(i));
        }
        return final_distance_ranges;
    }
}
