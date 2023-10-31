package vn.com.dsk.demo.base.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.dto.UserDto;
import vn.com.dsk.demo.base.dto.request.UserRequest;
import vn.com.dsk.demo.base.dto.response.JwtResponse;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.base.exception.ServiceException;
import vn.com.dsk.demo.base.model.Authority;
import vn.com.dsk.demo.base.model.User;
import vn.com.dsk.demo.base.repository.AuthorityRepository;
import vn.com.dsk.demo.base.repository.UserRepository;
import vn.com.dsk.demo.base.security.jwt.JwtUtils;
import vn.com.dsk.demo.base.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UserDto getUserInfo() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new RuntimeException("Not found user with username: " + username));
    }

    @Override
    public JwtResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail()))
            throw new ServiceException("Email or username is existed in system", "err.api.email-username-is-existed");
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Set<String> listRole = userRequest.getRoles();

        Set<Authority> roles = new HashSet<>();

        if (listRole != null && !listRole.isEmpty()) {
            listRole.forEach(role -> roles.add(authorityRepository.findByRole(role).orElseThrow(() -> new EntityNotFoundException(AuthorityRepository.class.getName(), role))));
        }
        user.setAuthorities(roles);
        try {
            userRepository.save(user);
            return new JwtResponse(
                    jwtUtils.generateAccessToken(userRequest.getUsername()),
                    jwtUtils.generateRefreshToken(userRequest.getUsername()),
                    "Bearer",
                    userRequest.getUsername(),
                    listRole != null ? listRole.stream().toList() : null);
        } catch (DataAccessException e) {
            log.error("Error saving user to the database", e);
            throw new ServiceException("Failed to add user", "err.api.failed-to-add-user");
        }
    }
}
