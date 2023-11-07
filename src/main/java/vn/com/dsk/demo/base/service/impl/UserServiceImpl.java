package vn.com.dsk.demo.base.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.dto.AuthorityDto;
import vn.com.dsk.demo.base.dto.UserDto;
import vn.com.dsk.demo.base.dto.request.UserInfoRequest;
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
import vn.com.dsk.demo.feature.repository.BranchRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final BranchRepository branchRepository;

    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public UserDto getUserInfo() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .map(user -> {
                    var userDto = modelMapper.map(user, UserDto.class);
                    var authorityDtos = user.getAuthorities().stream().map(
                            authority -> modelMapper.map(authority, AuthorityDto.class)).toList();
                    userDto.setAuthorityDtos(authorityDtos);
                    return userDto;
                })
                .orElseThrow(() -> new RuntimeException("Not found user with username: " + username));
    }

    @Override
    @Transactional
    public JwtResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail()))
            throw new ServiceException("Email or username is existed in system", "err.api.email-username-is-existed");
        User user = modelMapper.map(userRequest, User.class);
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

    @Override
    @Transactional
    public UserDto updateInfo(UserInfoRequest userInfoRequest) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByUsername(username).orElseThrow();

        user.setEmail(!userInfoRequest.getEmail().isEmpty() ? userInfoRequest.getEmail() : user.getEmail());
        user.setAddress(!userInfoRequest.getAddress().isEmpty() ? userInfoRequest.getAddress() : user.getAddress());
        user.setFullName(!userInfoRequest.getFullName().isEmpty() ? userInfoRequest.getFullName() : user.getFullName());
        if (userInfoRequest.getDob() != null) {
            user.setDob(userInfoRequest.getDob());
        }

        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public String updateWorkplace(String branchId) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByUsername(username).orElseThrow();
        var branch = branchRepository.findById(branchId).orElseThrow();
        user.setBranch(branch);
        userRepository.save(user);
        return "Workplace of user have been update";
    }

    @Override
    @Transactional
    public List<UserDto> getAllUser() {
        return userRepository.findAllByIsActive(true).stream()
                .map(user -> {
                    var userDto = modelMapper.map(user, UserDto.class);
                    var authorityDtos = user.getAuthorities().stream().map(
                            authority -> modelMapper.map(authority, AuthorityDto.class)).toList();
                    userDto.setAuthorityDtos(authorityDtos);
                    return userDto;
                }).toList();
    }

    @Override
    @Transactional
    public UserDto getUserInfoById(String userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    var userDto = modelMapper.map(user, UserDto.class);
                    var authorityDtos = user.getAuthorities().stream().map(
                            authority -> modelMapper.map(authority, AuthorityDto.class)).toList();
                    userDto.setAuthorityDtos(authorityDtos);
                    return userDto;
                })
                .orElseThrow(() -> new RuntimeException("Not found user with id: " + userId));
    }

    @Override
    @Transactional
    public String deactivateUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Not found user with username: " + userId));
        user.setIsActive(false);
        return "Deactivate user with id " + userId;
    }
}
