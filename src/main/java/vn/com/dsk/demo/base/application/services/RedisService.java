package vn.com.dsk.demo.base.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.adapter.dto.request.RegisterInfo;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

        private final RedisTemplate<String, Object> redisTemplate;

        private final ObjectMapper objectMapper;

        public void saveToken(String token, String userData, long duration) {
            redisTemplate.opsForValue().set(token, userData, duration, TimeUnit.SECONDS);
        }

        public void saveToken(String token, RegisterInfo registerInfo, long duration) {
            redisTemplate.opsForValue().set(token, registerInfo, duration, TimeUnit.SECONDS);
        }

        public RegisterInfo getRegisterInfoByToken(String token) {
            Object value = redisTemplate.opsForValue().get(token);
            return objectMapper.convertValue(value, RegisterInfo.class);
        }

        public void revokeToken(String token) {
            redisTemplate.delete(token);
        }
}
