package com.demo.example.data.cache;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public abstract class AbstractRedisCache<K,V> implements InitializingBean {

    @Autowired
    private RedisTemplate<K,V> redisTemplate;

    private RedisSerializer<K> keySerializer;

    private RedisSerializer<V> valueSerializer;

    protected abstract RedisSerializer<K> keySerializer();

    protected abstract RedisSerializer<V> valueSerializer();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.keySerializer = keySerializer();
        this.valueSerializer = valueSerializer();
        if (null == this.keySerializer || null == this.valueSerializer) {
            throw new BeanInitializationException("null == keySerializer || null == valueSerializer");
        }
    }

    public V get(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void put(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

}
