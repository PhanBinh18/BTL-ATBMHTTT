package com.example.demo_sqli.service;
import com.example.demo_sqli.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Vulnerable (error-based target)
    public List<Map<String, Object>> getUserVulnerable(String id) {
    String sql = "SELECT * FROM users WHERE id = " + id;
    System.out.println(">>> SQL: " + sql);
    return jdbcTemplate.queryForList(sql);
}

    // Safe version (để so sánh)
    public List<Map<String, Object>> getUserSafe(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForList(sql, id);
    }
}