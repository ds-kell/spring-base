package vn.com.dsk.demo.base.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vn.com.dsk.demo.base.dto.request.*;
import vn.com.dsk.demo.base.dto.response.JwtResponse;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.base.exception.ServiceException;
import vn.com.dsk.demo.base.model.Account;
import vn.com.dsk.demo.base.model.Authority;
import vn.com.dsk.demo.base.repository.AccountRepository;
import vn.com.dsk.demo.base.repository.AuthorityRepository;
import vn.com.dsk.demo.base.repository.PasswordResetTokenRepository;
import vn.com.dsk.demo.base.security.impl.UserDetailsServiceImpl;
import vn.com.dsk.demo.base.security.jwt.JwtUtils;
import vn.com.dsk.demo.base.service.AuthService;
import vn.com.dsk.demo.base.service.EmailService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final EmailService emailService;

    @Override
    @Transactional
    public String signup(SignupRequest signupRequest) {
        if (accountRepository.existsByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail()))
            throw new ServiceException("Email or username is existed in system", "err.api.email-username-is-existed");
        emailService.sendVerifyCode(signupRequest);
        return "Check your email";
    }

    @Override
    public JwtResponse verifySignUp(VerifySignUp verifySignUp) {
        if(verifySignUp.getVerifyCode().equals("")){
            throw new ServiceException("Verification code is incorrect", "err.api.verifyCode-incorrect");
        }
        Account user = new Account();
        user.setUsername(verifySignUp.getUsername());
        user.setEmail(verifySignUp.getEmail());
        user.setPassword(passwordEncoder.encode(verifySignUp.getPassword()));

        Set<String> listAuthority = verifySignUp.getAuthorities();
        Set<Authority> authorities = new HashSet<>();

        if (listAuthority != null && !listAuthority.isEmpty()) {
            listAuthority.forEach(permission -> authorities.add(authorityRepository.findByName(permission).orElseThrow(() -> new EntityNotFoundException(AuthorityRepository.class.getName(), permission))));
        }
        user.setAuthorities(authorities);
        try {
            accountRepository.save(user);
            return new JwtResponse(
                    jwtUtils.generateAccessToken(verifySignUp.getUsername()),
                    jwtUtils.generateRefreshToken(verifySignUp.getUsername()),
                    "Bearer",
                    verifySignUp.getUsername(),
                    listAuthority != null ? listAuthority.stream().toList() : null);
        } catch (DataAccessException e) {
            log.error("Error saving user to the database", e);
            throw new ServiceException("Failed to add user", "err.api.failed-to-add-user");
        }
    }

    @Override
    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new JwtResponse(
                    jwtUtils.generateAccessToken(userDetails),
                    jwtUtils.generateRefreshToken(userDetails),
                    "Bearer",
                    userDetails.getUsername(),
                    authorities);

        } catch (AuthenticationException authenticationException) {
            throw new ServiceException("Username or password is invalid", "err.authorize.unauthorized");
        }
    }

    @Override
    public JwtResponse verifyExpiration(String refreshToken) {
        final String space = "\\s+";
        String token = "";
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer ")) {
            token = refreshToken.split(space)[1];
        }
        if (jwtUtils.validateToken(token)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtils.extractUsername(token));
            List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new JwtResponse(
                    jwtUtils.generateAccessToken(userDetails),
                    jwtUtils.generateRefreshToken(userDetails),
                    "Bearer",
                    userDetails.getUsername(),
                    authorities);
        } else {
            throw new ServiceException("Login session has expired", "err.token.expired");
        }
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Optional<Account> account = accountRepository.findByEmail(forgotPasswordRequest.getEmail());
        if(account.isPresent()){
            String token = jwtUtils.generateResetPasswordToken(account.get().getUsername());
            System.out.println(token);
            String url = "http://localhost:8088/api/private/auth/reset-password/?token=" + token;
            return "Password reset email has been sent";
        }
        else {
            return "Email is not registered";
        }
    }

    @Override
    public JwtResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        return null;
    }
}

