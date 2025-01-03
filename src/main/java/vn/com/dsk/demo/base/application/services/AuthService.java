package vn.com.dsk.demo.base.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vn.com.dsk.demo.base.adapter.dto.request.*;
import vn.com.dsk.demo.base.adapter.dto.response.JwtResponse;
import vn.com.dsk.demo.base.infrastructure.exception.EntityNotFoundException;
import vn.com.dsk.demo.base.infrastructure.exception.ServiceException;
import vn.com.dsk.demo.base.domain.entities.Account;
import vn.com.dsk.demo.base.domain.entities.Authority;
import vn.com.dsk.demo.base.infrastructure.persistence.repository.AccountJpaRepository;
import vn.com.dsk.demo.base.infrastructure.persistence.repository.AuthorityJpaRepository;
import vn.com.dsk.demo.base.infrastructure.persistence.repository.PasswordResetTokenJpaRepository;
import vn.com.dsk.demo.base.security.impl.UserDetailsServiceImpl;
import vn.com.dsk.demo.base.security.jwt.JwtUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AccountJpaRepository accountJpaRepository;

    private final AuthorityJpaRepository authorityJpaRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final EmailService emailService;

    private final RedisService redisService;

    @Transactional
    public boolean checkEmailOrUserNameExistInSystem(RegisterInfo registerInfo) {
        return accountJpaRepository.existsByUsernameOrEmail(registerInfo.getUsername(), registerInfo.getEmail());
    }

    public Set<Authority> getPermissionByName(Set<String> listName) {
        Set<Authority> authorities = new HashSet<>();
        listName.forEach(permission -> authorities.add(
                authorityJpaRepository.findByName(permission).orElseThrow(() -> new EntityNotFoundException(AuthorityJpaRepository.class.getName(), permission))));
        return authorities;
    }

    public void registerNewAccount(Account user) {
        try {
            accountJpaRepository.save(user);
        } catch (DataAccessException e) {
            log.error("Error saving user to the database", e);
            throw new ServiceException("Failed to add user", "err.api.failed-to-add-user");
        }
    }

    public Object verifyRegister(String OTP) {
        RegisterInfo registerInfo = redisService.getRegisterInfoByToken(OTP);
        if (registerInfo != null) {
            Account user = new Account();
            user.setUsername(registerInfo.getUsername());
            user.setEmail(registerInfo.getEmail());
            user.setPassword(passwordEncoder.encode(registerInfo.getPassword()));
            user.setIsActive(1);
            Set<String> listAuthority = registerInfo.getAuthorities();
            Set<Authority> authorities = new HashSet<>();

            if (listAuthority != null && !listAuthority.isEmpty()) {
                listAuthority.forEach(permission -> authorities.add(authorityJpaRepository.findByName(permission)
                        .orElseThrow(() -> new EntityNotFoundException(AuthorityJpaRepository.class.getName(), permission))));
            }
            user.setAuthorities(authorities);
            try {
                accountJpaRepository.save(user);
                return new JwtResponse(jwtUtils.generateAccessToken(registerInfo.getUsername()), jwtUtils.generateRefreshToken(registerInfo.getUsername()),
                        "Bearer", registerInfo.getUsername(), listAuthority != null ? listAuthority.stream().toList() : null);
            } catch (DataAccessException e) {
                log.error("Error saving user to the database", e);
                throw new ServiceException("Failed to add user", "err.api.failed-to-add-user");
            }
        } else {
            return "OTP incorrect";
        }
    }

    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return new JwtResponse(jwtUtils.generateAccessToken(userDetails), jwtUtils.generateRefreshToken(userDetails), "Bearer", userDetails.getUsername(),
                authorities);
    }

    public JwtResponse verifyExpiration(String refreshToken) {
        final String space = "\\s+";
        String token = "";
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer ")) {
            token = refreshToken.split(space)[1];
        }
        if (Boolean.TRUE.equals(jwtUtils.validateToken(token))) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtils.extractUsername(token));
            List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new JwtResponse(jwtUtils.generateAccessToken(userDetails), jwtUtils.generateRefreshToken(userDetails), "Bearer", userDetails.getUsername(),
                    authorities);
        } else {
            throw new ServiceException("Login session has expired", "err.token.expired");
        }
    }

    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Optional<Account> account = accountJpaRepository.findByEmail(forgotPasswordRequest.getEmail());
        if (account.isPresent()) {
            String token = jwtUtils.generateResetPasswordToken(account.get().getUsername());
            String url = "http://localhost:8088/api/private/auth/reset-password/?token=" + token;
            return "Password reset email has been sent";
        } else {
            return "Email is not registered";
        }
    }

    public JwtResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        return null;
    }
}