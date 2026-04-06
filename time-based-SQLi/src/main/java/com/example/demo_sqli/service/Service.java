package com.example.demo_sqli.service;

import com.example.demo_sqli.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private Repository repository;

    public boolean login(String username, String password) {
        return repository.login(username, password);
    }

    public void deleteProductVulnerable(String id) {
        repository.deleteProductVulnerable(id);
    }

    public boolean deleteProductSecure(String id) {
        try {
            // Khắc phục: Ép kiểu nghiêm ngặt sang số nguyên.
            // Nếu hacker nhập "1; DROP TABLE users", hàm parseInt sẽ ném lỗi ngay lập tức.
            int productId = Integer.parseInt(id);
            int rowsAffected = repository.deleteProductSecure(productId);
            return rowsAffected > 0;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID không hợp lệ! Vui lòng nhập số nguyên.");
        }
    }
}
