package vn.com.dsk.demo.base.infrastructure.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
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
import vn.com.dsk.demo.base.adapter.dto.request.*;
import vn.com.dsk.demo.base.adapter.dto.response.JwtResponse;
import vn.com.dsk.demo.base.application.services.OTPService;
import vn.com.dsk.demo.base.infrastructure.exception.EntityNotFoundException;
import vn.com.dsk.demo.base.infrastructure.exception.ServiceException;
import vn.com.dsk.demo.base.domain.entities.Account;
import vn.com.dsk.demo.base.domain.entities.Authority;
import vn.com.dsk.demo.base.infrastructure.repository.AccountRepository;
import vn.com.dsk.demo.base.infrastructure.repository.AuthorityRepository;
import vn.com.dsk.demo.base.infrastructure.repository.PasswordResetTokenRepository;
import vn.com.dsk.demo.base.security.impl.UserDetailsServiceImpl;
import vn.com.dsk.demo.base.security.jwt.JwtUtils;
import vn.com.dsk.demo.base.application.services.AuthService;
import vn.com.dsk.demo.base.application.services.EmailService;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;


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

    private final OTPService.RedisService redisService;

    @Override
    @Transactional
    public String signup(SignupRequest signupRequest) {
        if (Boolean.TRUE.equals(accountRepository.existsByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail())))
            throw new ServiceException("Email or username is existed in system", "err.api.email-username-is-existed");
        String OTP = generateOTP();
        redisService.saveToken(OTP, signupRequest, 300);
        emailService.sendVerifyCode(signupRequest, OTP);
        return "Check your email";
    }

    @Override
    public JwtResponse verifySignUp(VerifySignUp verifySignUp) {
        if(verifySignUp.getVerifyCode().isEmpty()){
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
    public Object verifySignUp(String OTP) {
        SignupRequest signupRequest = redisService.validateToken(OTP);
        if(signupRequest != null){
            Account user = new Account();
            user.setUsername(signupRequest.getUsername());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setIsActive(1);
            Set<String> listAuthority = signupRequest.getAuthorities();
            Set<Authority> authorities = new HashSet<>();

            if (listAuthority != null && !listAuthority.isEmpty()) {
                listAuthority.forEach(permission -> authorities.add(authorityRepository.findByName(permission).orElseThrow(() -> new EntityNotFoundException(AuthorityRepository.class.getName(), permission))));
            }
            user.setAuthorities(authorities);
            try {
                accountRepository.save(user);
                return new JwtResponse(
                        jwtUtils.generateAccessToken(signupRequest.getUsername()),
                        jwtUtils.generateRefreshToken(signupRequest.getUsername()),
                        "Bearer",
                        signupRequest.getUsername(),
                        listAuthority != null ? listAuthority.stream().toList() : null);
            } catch (DataAccessException e) {
                log.error("Error saving user to the database", e);
                throw new ServiceException("Failed to add user", "err.api.failed-to-add-user");
            }
        } else {
            return "OTP incorrect";
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
        if (Boolean.TRUE.equals(jwtUtils.validateToken(token))) {
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

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private String generateOTP() {
        StringBuilder otp = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            otp.append(randomChar);
        }
        return otp.toString();
    }

    @Service
    @RequiredArgsConstructor
    public static class RedisServiceImpl implements OTPService.RedisService {

        private final RedisTemplate<String, Object> redisTemplate;

        private final ObjectMapper objectMapper;
        @Override
        public void saveToken(String token, String userData, long duration) {
            redisTemplate.opsForValue().set(token, userData, duration, TimeUnit.SECONDS);
        }

        @Override
        public void saveToken(String token, SignupRequest signupRequest, long duration) {
            redisTemplate.opsForValue().set(token, signupRequest, duration, TimeUnit.SECONDS);
        }

        @Override
        public SignupRequest validateToken(String token) {
            Object value = redisTemplate.opsForValue().get(token);
            return objectMapper.convertValue(value, SignupRequest.class);
        }

        @Override
        public void revokeToken(String token) {
            redisTemplate.delete(token);
        }
    }
}

