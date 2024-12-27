package vn.com.dsk.demo.base.adapter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.adapter.dto.request.*;
import vn.com.dsk.demo.base.application.services.AuthService;
import vn.com.dsk.demo.base.adapter.wrappers.Response;
import vn.com.dsk.demo.base.adapter.wrappers.ResponseUtils;
import vn.com.dsk.demo.base.application.usecases.LoginUseCase;
import vn.com.dsk.demo.base.application.usecases.PreRegisterUseCase;
import vn.com.dsk.demo.base.application.usecases.VerifyRegisterUseCase;

@RequiredArgsConstructor
/*    Tự động tạo một constructor cho tất cả các biến instance:
      - Có từ khóa `final`.
      - Hoặc có annotation `@NonNull`.
      */
@RestController
@RequestMapping("/api/")
public class AuthController {

    private final AuthService authService;

    private final PreRegisterUseCase preRegisterUseCase;

    private final VerifyRegisterUseCase verifyRegisterUseCase;

    private final LoginUseCase loginUseCase ;

    @PostMapping("public/auth/login")
    public ResponseEntity<Response> authenticateAccount(@Valid @RequestBody LoginRequest loginRequest) {
        return loginUseCase.execute(loginRequest);
    }

    @PostMapping("public/auth/pre-register")
    public ResponseEntity<Response> registerAccount(@Valid @RequestBody RegisterInfo registerInfo) {
        return ResponseUtils.ok(preRegisterUseCase.execute(registerInfo));
    }

    @PostMapping("public/auth/verify-register")
    public ResponseEntity<Response> verifySignUp(@Valid @RequestBody VerifyRegisterInfo verifyRegisterInfo) {
        return ResponseUtils.ok(verifyRegisterUseCase.execute(verifyRegisterInfo));
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