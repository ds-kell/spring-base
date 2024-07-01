package vn.com.dsk.demo.base.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.dto.request.*;
import vn.com.dsk.demo.base.service.AuthService;
import vn.com.dsk.demo.base.utils.response.Response;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("public/auth/login")
    public ResponseEntity<Response> authenticateAccount(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseUtils.ok(authService.login(loginRequest));
    }

    @PostMapping("public/auth/signup")
    public ResponseEntity<Response> registerAccount(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseUtils.ok(authService.signup(signupRequest));
    }

    @PostMapping("public/auth/verify-signup")
    public ResponseEntity<Response> verifySignUp(@Valid @RequestBody VerifySignUp verifySignUp) {
        return ResponseUtils.ok(authService.verifySignUp(verifySignUp));
    }

    @PostMapping("public/auth/verify-otp")
    public ResponseEntity<Response> verifySignUp(@Valid @RequestParam  String OTP) {
        return ResponseUtils.ok(authService.verifySignUp(OTP));
    }

    @GetMapping("private/auth/refresh-token")
    public ResponseEntity<Response> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        return ResponseUtils.ok("verified", authService.verifyExpiration(refreshToken));
    }

    @PostMapping("private/auth/change-password")
    public ResponseEntity<Response> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseUtils.ok(authService.changePassword(changePasswordRequest));
    }

    @PostMapping("public/auth/forgot-password")
    public ResponseEntity<Response> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return ResponseUtils.ok(authService.forgotPassword(forgotPasswordRequest));
    }
}
