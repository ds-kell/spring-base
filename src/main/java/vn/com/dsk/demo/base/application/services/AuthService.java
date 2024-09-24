package vn.com.dsk.demo.base.application.services;


import vn.com.dsk.demo.base.adapter.dto.request.*;
import vn.com.dsk.demo.base.adapter.dto.response.JwtResponse;

public interface AuthService {

    String signup(SignupRequest signupRequest);

    JwtResponse verifySignUp(VerifySignUp verifySignUp);

    Object verifySignUp(String OTP);

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse verifyExpiration(String refreshToken);

    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    JwtResponse changePassword(ChangePasswordRequest changePasswordRequest);
}
