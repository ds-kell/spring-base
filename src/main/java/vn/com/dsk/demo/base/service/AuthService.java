package vn.com.dsk.demo.base.service;


import vn.com.dsk.demo.base.dto.request.*;
import vn.com.dsk.demo.base.dto.response.JwtResponse;

public interface AuthService {

    String signup(SignupRequest signupRequest);

    JwtResponse verifySignUp(VerifySignUp verifySignUp);

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse verifyExpiration(String refreshToken);

    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    JwtResponse changePassword(ChangePasswordRequest changePasswordRequest);
}
