package org.wolt.dopcapi3.util;

import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import org.json.JSONObject;

@Service
public class CacheService {

    private final RedisClient redisClient;

    public CacheService() {
        HostAndPort hostAndPort = new HostAndPort("localhost", 6379);
        this.redisClient = new RedisClient.Builder().hostAndPort(hostAndPort).build();
    }

    public JSONObject getCacheData(String address){
        try {
            return new JSONObject(redisClient.get(address));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void cacheResult(String key, JSONObject order) {
        redisClient.set(key, order.toString());
        redisClient.expire(key, 60);
    }
}
