package com.kcs.community.repository.user;

import com.kcs.community.entity.User;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public Long saveUser(User user) {
        String sql = "INSERT INTO users (email, password, nickname, profile_url, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        log.info("email:{}, password:{}, nickname:{}, role: {}, created:{}, updated:{}", user.getEmail(), user.getPassword(),
                user.getNickname(), user.getRole().name(), user.getCreatedAt(), user.getUpdatedAt());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNickname());
            ps.setString(4, user.getProfileUrl());
            ps.setString(5, user.getRole().name());
            ps.setTimestamp(6, Timestamp.valueOf(user.getCreatedAt().withNano(0)));
            ps.setTimestamp(7, Timestamp.valueOf(user.getUpdatedAt().withNano(0)));

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<User> findById(Long userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper, userId));
        } catch (EmptyResultDataAccessException e) {
            log.error("findById: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            log.error("findByEmail: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        String sql = "SELECT * FROM users WHERE nickname = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper, nickname));
        } catch (EmptyResultDataAccessException e) {
            log.error("findByNickname: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public void updateUser(Long userId, User updateUser) {
        String sql = "UPDATE users SET password = ?, nickname = ?, profile_url = ? WHERE user_id = ?";
        String password = updateUser.getPassword();
        String nickname = updateUser.getNickname();
        String profileUrl = updateUser.getProfileUrl();
        jdbcTemplate.update(sql, password, nickname, profileUrl, userId);
    }

    @Override
    public void deleteUser(Long userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return findByNickname(nickname).isPresent();
    }
}
