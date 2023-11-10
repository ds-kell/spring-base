package vn.com.dsk.demo.base.security.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.model.User;
import vn.com.dsk.demo.base.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> account = new EmailValidator().isValid(username, null)
                ? userRepository.findOneWithAuthoritiesByEmail(username)
                : userRepository.findOneWithAuthoritiesByUsername(username);

        return account
                .map(this::createUserSecurity)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' is not exist in system"));
    }

    private org.springframework.security.core.userdetails.User createUserSecurity(User user) {
        Set<GrantedAuthority> securityAuthorities = new HashSet<>();
        securityAuthorities.add(new SimpleGrantedAuthority(user.getAuthorities()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), securityAuthorities);

    }
}
