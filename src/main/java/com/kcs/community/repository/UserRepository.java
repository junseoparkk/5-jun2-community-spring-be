package com.kcs.community.repository;

import com.kcs.community.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Long saveUser(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    List<User> findAll();
    void updateUser(Long userId, User updateUser);
    void deleteUser(Long userId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
