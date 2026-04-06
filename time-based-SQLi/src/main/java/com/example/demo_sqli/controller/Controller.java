package com.example.demo_sqli.controller;

import com.example.demo_sqli.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Provider;

@RestController
public class Controller {
    @Autowired
    private Service service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam
    String username, @RequestParam String password){
        boolean loginSuccess = service.login(username, password);
        if (loginSuccess){
            return ResponseEntity.ok("Success");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fail");
        }
    }
    // API 1: Mở để tấn công
    @GetMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam String id) {
        try {
            service.deleteProductVulnerable(id);
            return ResponseEntity.ok("Đã thực thi lệnh xóa sản phẩm!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi Server: " + e.getMessage());
        }
    }

    // API 2: Đã bảo mật
    @GetMapping("/deleteProductSecure")
    public ResponseEntity<?> deleteProductSecure(@RequestParam String id) {
        try {
            boolean success = service.deleteProductSecure(id);
            if (success) {
                return ResponseEntity.ok("Xóa sản phẩm thành công và an toàn.");
            } else {
                return ResponseEntity.ok("Không tìm thấy sản phẩm để xóa.");
            }
        } catch (IllegalArgumentException e) {
            // Bắt lỗi khi hacker cố tình chèn mã SQL vào ID
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BLOCKED: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi Server!");
        }
    }
}
