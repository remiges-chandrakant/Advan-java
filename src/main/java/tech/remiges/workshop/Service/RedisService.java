package tech.remiges.workshop.Service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // Write a value to Redis with TTL (expire after a specified time)
    public void setValueWithTTL(String key, String value, long ttlInSeconds) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, value, ttlInSeconds, TimeUnit.SECONDS);
    }

    // Read a value from Redis
    public String getValue(String key) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        return valueOps.get(key);
    }

    // Increment the value of a key by a specific amount
    public Long incrementValue(String key, long delta) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        return valueOps.increment(key, delta);
    }

    // Decrement the value of a key by a specific amount
    public Long decrementValue(String key, long delta) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        return valueOps.increment(key, -delta);
    }

    // Set TTL (expire after a specified time) for a key
    public Boolean setTTL(String key, long ttlInSeconds) {
        return redisTemplate.expire(key, ttlInSeconds, TimeUnit.SECONDS);
    }

    // Get TTL (time to live) for a key
    public Long getTTL(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    // Remove TTL (cancel expiration) for a key
    public Boolean removeTTL(String key) {
        return redisTemplate.persist(key);
    }
}
