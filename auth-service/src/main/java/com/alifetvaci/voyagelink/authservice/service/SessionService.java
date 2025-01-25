package com.alifetvaci.voyagelink.authservice.service;

import com.alifetvaci.voyagelink.authservice.redis.model.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SessionService extends RedisService<Session> {

    public SessionService(RedisTemplate<String, Session> redisTemplate) {
        super(redisTemplate);
    }

}