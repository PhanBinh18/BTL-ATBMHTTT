package com.example.demo_sqli.controller;

import com.example.demo_sqli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/vuln")
    public Object vulnerable(@RequestParam String id) {
        try {
            return userService.getUserVulnerable(id);
        } catch (Exception e) {
            return "ERROR: " + e.toString();
        }
    }

    @GetMapping("/safe")
    public Object safe(@RequestParam String id) {
        try {
            return userService.getUserSafe(id);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}