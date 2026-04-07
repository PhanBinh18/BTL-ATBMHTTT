package com.example.demo_sqli.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

@org.springframework.stereotype.Repository
public class Repository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${app.security.mode}")
    private String mode;

    public boolean login(String username, String password) {

        System.out.println("====== MODE: " + mode + " ======");

        // 8081 cho docker, 8080 run thuong
        // dung: http://localhost:8081/login?username=admin&password=admin123
        try {
            // ================= VULN =================
            // http://localhost:8081/login?username=admin' OR '1'='1&password=abc
            if (mode.equals("VULN")) {
                String sql = "SELECT count(*) FROM users WHERE username = '"
                        + username + "' AND password = '" + password + "'";

                System.out.println("[SQL VULN]: " + sql);

                int count = jdbcTemplate.queryForObject(sql, Integer.class);
                return count > 0;
            }

            // ================= FILTER =================
            // http://localhost:8081/login?username=admin%5C&password=%20OR%201=1%20--%20
            // "http://localhost:8081/login?username=admin\&password= OR 1=1 -- "
            // ma hoa hoan toan:
            // http://localhost:8081/login?username=admin%5C&password=%20%6f%72%20%31%3d%31%20%23 
            else if (mode.equals("FILTER")) {
                username = username.replace("'", "").replace("--", "").replace("OR", "").replace("AND", "");
                password = password.replace("'", "").replace("--", "").replace("OR", "").replace("AND", "");

                String sql = "SELECT count(*) FROM users WHERE username = '"
                        + username + "' AND password = '" + password + "'";

                System.out.println("[SQL FILTER]: " + sql);

                try {
                    int count = jdbcTemplate.queryForObject(sql, Integer.class);
                    return count > 0;
                } catch (Exception e) {
                    return false;
                }
            }

            // ================= SECURE =================
            else {
                String sql = "SELECT count(*) FROM users WHERE username = ? AND password = ?";

                System.out.println("[SQL SECURE]: " + sql);

                Integer count = jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        username,
                        password
                );

                return count != null && count > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}