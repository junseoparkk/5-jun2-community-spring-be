package com.kcs.community.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kcs.community.entity.RoleType;
import com.kcs.community.entity.User;
import com.kcs.community.repository.user.JdbcUserRepository;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
public class JdbcUserRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcUserRepository repository;

    @BeforeEach
    public void setUp() {
        // AUTO_INCREMENT 초기화
        jdbcTemplate.execute("ALTER TABLE users AUTO_INCREMENT = 1");
    }

    @Test
    void saveUserTest() {
        //given
        User user = User.builder()
                .email("test@test.com")
                .password("testPassword!")
                .nickname("testName")
                .profileUrl("http://profile.com/test")
                .role(RoleType.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        //when
        Long userId = repository.save(user);
        User findUser = repository.findById(userId).get();

        //then
        log.info("user = {}", findUser);
        assertThat(findUser.getNickname()).isEqualTo(user.getNickname());
    }

    @Test
    void updateUserTest() {
        //given
        User user = generateTestUser(1);
        Long userId = repository.save(user);

        //when
        User updateUser = generateTestUser(2);
        repository.updateUser(userId, updateUser);

        //then
        User findUser = repository.findById(userId).get();
        assertThat(findUser.getNickname()).isEqualTo(updateUser.getNickname());
    }

    @Test
    void deleteUserTest() {
        //given
        User user = generateTestUser(1);
        Long userId = repository.save(user);

        //when
        repository.deleteUser(userId);

        //then
        assertThatThrownBy(() -> repository.findById(userId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    private User generateTestUser(int number) {
        User user = User.builder()
                .email("test@test.com - " + number)
                .password("testPassword! - " + number)
                .nickname("testName - " + number)
                .profileUrl("http://profile.com/test - " + number)
                .role(RoleType.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return user;
    }
}
