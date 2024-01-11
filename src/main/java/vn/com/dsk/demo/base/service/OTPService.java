package vn.com.dsk.demo.base.service;

public interface OTPService {

    String generateOTP();

    boolean verifyOTP(String phoneNumber, String otpCode);

    boolean isRateLimited(String rateLimitKey);
}
