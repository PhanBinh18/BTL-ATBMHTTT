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
            else if (mode.equals("FILTER")) {
                username = username.replace("'", "");
                password = password.replace("'", "");

                String sql = "SELECT count(*) FROM users WHERE username = '"
                        + username + "' AND password = '" + password + "'";

                System.out.println("[SQL FILTER]: " + sql);

                int count = jdbcTemplate.queryForObject(sql, Integer.class);
                return count > 0;
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
    // 1. Hàm có lỗ hổng (Vulnerable)
    public void deleteProductVulnerable(String id) {
        // Lỗi: Ghép chuỗi trực tiếp từ input
        String sql = "DELETE FROM products WHERE id = " + id;

        System.out.println("[SQL VULNERABLE DELETE]: " + sql);

        // Dùng execute() sẽ cho phép chạy nhiều câu lệnh (Stacked Queries) nếu DB có allowMultiQueries=true
        jdbcTemplate.execute(sql);
    }

    // 2. Hàm bảo mật (Secure)
    public int deleteProductSecure(int id) {
        // Khắc phục: Dùng PreparedStatement với dấu ?
        String sql = "DELETE FROM products WHERE id = ?";

        System.out.println("[SQL SECURE DELETE]: " + sql);

        // Dùng update() an toàn, tự động mã hóa tham số
        return jdbcTemplate.update(sql, id);
    }

}