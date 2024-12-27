package vn.com.dsk.demo.base.application.usecases;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.adapter.dto.request.LoginRequest;
import vn.com.dsk.demo.base.adapter.wrappers.Response;
import vn.com.dsk.demo.base.application.services.AuthService;

@Service
public class LoginUseCase implements UseCase<ResponseEntity<Response> , LoginRequest> {

    private final AuthService authService;

    public LoginUseCase(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<Response> execute(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}