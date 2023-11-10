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
import vn.com.dsk.demo.base.dto.request.LoginRequest;
import vn.com.dsk.demo.base.dto.request.SignupRequest;
import vn.com.dsk.demo.base.dto.response.JwtResponse;
import vn.com.dsk.demo.base.exception.ServiceException;
import vn.com.dsk.demo.base.model.User;
import vn.com.dsk.demo.base.repository.UserRepository;
import vn.com.dsk.demo.base.security.impl.UserDetailsServiceImpl;
import vn.com.dsk.demo.base.security.jwt.JwtUtils;
import vn.com.dsk.demo.base.service.AuthService;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    @Transactional
    public JwtResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail()))
            throw new ServiceException("Email or username is existed in system", "err.api.email-username-is-existed");
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setAuthorities(signupRequest.getRole());
        try {
            userRepository.save(user);
            return new JwtResponse(
                    jwtUtils.generateAccessToken(signupRequest.getUsername()),
                    jwtUtils.generateRefreshToken(signupRequest.getUsername()),
                    "Bearer",
                    signupRequest.getUsername(),
                    user.getAuthorities() != null ? user.getAuthorities() : null);
        } catch (DataAccessException e) {
            log.error("Error saving user to the database", e);
            throw new ServiceException("Failed to add user", "err.api.failed-to-add-user");
        }
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new JwtResponse(
                    jwtUtils.generateAccessToken(userDetails),
                    jwtUtils.generateRefreshToken(userDetails),
                    "Bearer",
                    userDetails.getUsername(),
                    roles.get(0));

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
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new JwtResponse(
                    jwtUtils.generateAccessToken(userDetails),
                    jwtUtils.generateRefreshToken(userDetails),
                    "Bearer",
                    userDetails.getUsername(),
                    roles.get(0));
        } else {
            throw new ServiceException("Login session has expired", "err.token.expired");
        }
    }
}

