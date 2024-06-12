package com.kcs.community.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class DBConnectionTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testConnection() {
        String sql = "SELECT 1";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        assertTrue(result != null && result == 1, "Successfully connected database : mysql");
    }
}
