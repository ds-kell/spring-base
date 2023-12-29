package vn.com.dsk.demo.base.service;


import vn.com.dsk.demo.base.dto.request.ChangePasswordRequest;
import vn.com.dsk.demo.base.dto.request.ForgotPasswordRequest;
import vn.com.dsk.demo.base.dto.request.LoginRequest;
import vn.com.dsk.demo.base.dto.request.SignupRequest;
import vn.com.dsk.demo.base.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse signup(SignupRequest signupRequest);

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse verifyExpiration(String refreshToken);

    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    JwtResponse changePassword(ChangePasswordRequest changePasswordRequest);
}
