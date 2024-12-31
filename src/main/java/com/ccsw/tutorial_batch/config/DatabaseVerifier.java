package com.ccsw.tutorial_batch.config;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseVerifier {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseVerifier(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void verifyData() {
        System.out.println("Verifying data in 'game' table...");
        jdbcTemplate.query("SELECT * FROM game", (rs, rowNum) -> "ID: " + rs.getLong("id") + ", Title: " + rs.getString("title") + ", Age: " + rs.getInt("age") + ", Stock: " + rs.getInt("stock")).forEach(System.out::println);
    }
}
