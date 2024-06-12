package com.kcs.community.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kcs.community.entity.User;
import java.time.LocalDateTime;
import java.util.List;
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
                .build();

        //when
        Long userId = repository.saveUser(user);
        User findUser = repository.findById(userId).get();

        //then
        log.info("user = {}", findUser);
        assertThat(findUser.getNickname()).isEqualTo(user.getNickname());
    }

    @Test
    void findAllTest() {
        //given
        User user1 = generateTestUser(1);
        repository.saveUser(user1);

        User user2 = generateTestUser(2);
        repository.saveUser(user2);

        //when
        List<User> users = repository.findAll();

        //then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void updateUserTest() {
        //given
        User user = generateTestUser(1);
        Long userId = repository.saveUser(user);

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
        Long userId = repository.saveUser(user);

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
                .build();
        return user;
    }
}
