package com.kcs.community.service;

import com.kcs.community.dto.user.SignupRequest;
import com.kcs.community.dto.user.SignupResponse;
import com.kcs.community.entity.RoleType;
import com.kcs.community.entity.User;
import com.kcs.community.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest request) throws IllegalArgumentException {
        // 중복 이메일, 닉네임 확인 로직
        validateDuplicatedInfo(request);

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .profileUrl(request.profileUrl())
                .role(RoleType.USER)
                .createdAt(LocalDateTime.now().withNano(0))
                .updatedAt(LocalDateTime.now().withNano(0))
                .build();
        userRepository.saveUser(user);

        log.info("signup success - email: {}, nickname: {}, role: {}", user.getEmail(), user.getNickname(), user.getRole());
        return new SignupResponse(user.getEmail(), user.getNickname());
    }

    private void validateDuplicatedInfo(SignupRequest request) {
        try {
            userRepository.existsByEmail(request.email());
            userRepository.existsByNickname(request.nickname());
        } catch (EmptyResultDataAccessException e) {
            return;
        }
        throw new IllegalArgumentException("Duplicated User Information Error");
    }
}
