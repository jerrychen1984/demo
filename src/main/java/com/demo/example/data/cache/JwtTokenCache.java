package com.demo.example.data.cache;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenCache extends AbstractRedisCache<String, String> {

    @Override
    protected RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    @Override
    protected RedisSerializer<String> valueSerializer() {
        return new StringRedisSerializer();
    }

}
