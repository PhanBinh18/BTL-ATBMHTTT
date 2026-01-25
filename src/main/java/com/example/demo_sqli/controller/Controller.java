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
}
