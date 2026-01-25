package com.example.demo_sqli.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@org.springframework.stereotype.Repository


public class Repository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean login(String username, String password){
        String sql = "SELECT count(*) FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        System.out.println("--------------------------------------------------");
        System.out.println("[SQL EXECUTING - LOGIN]: " + sql);
        System.out.println("--------------------------------------------------");

        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
