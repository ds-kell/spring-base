package vn.com.dsk.demo.base.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.adapter.dto.request.LoginRequest;
import vn.com.dsk.demo.base.adapter.dto.response.JwtResponse;
import vn.com.dsk.demo.base.application.services.AuthService;
@Service

public class LoginUseCase implements UseCase<JwtResponse, LoginRequest> {

    private final AuthService authService;

    @Autowired
    public LoginUseCase(AuthService authService) {
        this.authService = authService;
    }
    @Override
    public JwtResponse execute(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
