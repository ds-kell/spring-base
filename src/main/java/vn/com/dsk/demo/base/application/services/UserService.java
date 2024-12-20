package vn.com.dsk.demo.base.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.adapter.dto.UserDto;
import vn.com.dsk.demo.base.infrastructure.persistence.repository.AccountJpaRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AccountJpaRepository accountJpaRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public UserDto getUserInfo() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountJpaRepository.findByUsername(username)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new RuntimeException("Not found user with username: " + username));
    }

}
