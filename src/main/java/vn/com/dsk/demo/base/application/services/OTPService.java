package vn.com.dsk.demo.base.application.services;

import vn.com.dsk.demo.base.adapter.dto.request.SignupRequest;

public interface OTPService {

    String generateOTP();

    boolean verifyOTP(String phoneNumber, String otpCode);

    boolean isRateLimited(String rateLimitKey);

    interface RedisService {

        void saveToken(String token, String userData, long duration);

        void saveToken(String token, SignupRequest signupRequest, long duration);

        SignupRequest validateToken(String token);

        void revokeToken(String token);
    }
}
