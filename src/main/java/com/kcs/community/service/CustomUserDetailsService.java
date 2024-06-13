package com.kcs.community.service;

import com.kcs.community.entity.User;
import com.kcs.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails findUser = userRepository.findByEmail(username)
                .map(this::generateUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Not Exist User"));
        log.info("findUser: {}, password: {}, authorities: {}", findUser.getUsername(), findUser.getPassword(), findUser.getAuthorities());
        return findUser;
    }

    private UserDetails generateUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
