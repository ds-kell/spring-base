package vn.com.dsk.demo.base.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.dto.request.SignupRequest;
import vn.com.dsk.demo.base.service.RedisService;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;
    @Override
    public void saveToken(String token, String userData, long duration) {
        redisTemplate.opsForValue().set(token, userData, duration, TimeUnit.SECONDS);
    }

    @Override
    public void saveToken(String token, SignupRequest signupRequest, long duration) {
        redisTemplate.opsForValue().set(token, signupRequest, duration, TimeUnit.SECONDS);
    }

    @Override
    public SignupRequest validateToken(String token) {
        Object value = redisTemplate.opsForValue().get(token);
        return objectMapper.convertValue(value, SignupRequest.class);
    }

    @Override
    public void revokeToken(String token) {
        redisTemplate.delete(token);
    }
}
