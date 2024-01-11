package vn.com.dsk.demo.base.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.service.OTPService;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

    private final StringRedisTemplate redisTemplate;

    private static final String OTP_PREFIX = "otp:";
    private static final String RATE_LIMIT_PREFIX = "rate_limit:";
    private static final int RATE_LIMIT = 5;
    private static final long RATE_LIMIT_PERIOD_SECONDS = 60;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public String generateOTP() {
       return null;
    }

    @Override
    public boolean verifyOTP(String phoneNumber, String otpCode) {
        return false;
    }

    @Override
    public boolean isRateLimited(String rateLimitKey) {
        return false;
    }
}
