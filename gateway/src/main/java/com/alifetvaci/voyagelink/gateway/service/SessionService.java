package com.alifetvaci.voyagelink.gateway.service;


import com.alifetvaci.voyagelink.gateway.redis.model.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SessionService extends RedisService<Session> {

    public SessionService(RedisTemplate<String, Session> redisTemplate) {
        super(redisTemplate);
    }

}