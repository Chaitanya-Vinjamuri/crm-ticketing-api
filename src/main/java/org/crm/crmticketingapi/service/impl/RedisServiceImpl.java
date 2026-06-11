package org.crm.crmticketingapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
public class RedisServiceImpl
        implements RedisService {

    private final RedisTemplate<String, Object>
            redisTemplate;

    @Override
    public void save(
            String key,
            Object value) {

        redisTemplate
                .opsForValue()
                .set(
                        key,
                        value
                );
    }

    @Override
    public Object get(
            String key) {

        return redisTemplate
                .opsForValue()
                .get(
                        key
                );
    }

    @Override
    public void delete(
            String key) {

        redisTemplate.delete(
                key
        );
    }
}