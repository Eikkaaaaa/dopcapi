package org.wolt.dopcapi3.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import org.json.JSONObject;

@Service
public class CacheService {

    private final RedisClient redisClient;

    public CacheService(
            @Value("${redis.host:127.0.0.1}") String redisHost,
            @Value("${redis.port:6379}") int redisPort
    ) {
        HostAndPort hostAndPort = new HostAndPort(redisHost, redisPort);
        this.redisClient = new RedisClient.Builder().hostAndPort(hostAndPort).build();
    }

    public JSONObject getCacheData(String address){
        try {
            String data = redisClient.get(address);
            if(data == null) return null;
            return new JSONObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cacheResult(String key, JSONObject order) {
        if (order != null) {
            redisClient.set(key, order.toString());
            redisClient.expire(key, 60);
        }
    }
}
