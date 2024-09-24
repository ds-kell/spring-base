package vn.com.dsk.demo.base.application.usecases;

import vn.com.dsk.demo.base.adapter.dto.request.SignupRequest;
import vn.com.dsk.demo.base.domain.entities.Account;
import vn.com.dsk.demo.base.infrastructure.repository.AccountRepository;

public class SignUpUserUseCase {
    private final AccountRepository accountRepository;

    public SignUpUserUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String execute(SignupRequest signupRequest) {
        Account user = new Account();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        accountRepository.save(user);
        return "User signed up successfully!";
    }
}
