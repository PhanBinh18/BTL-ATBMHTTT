package com.example.demo_sqli.service;

import com.example.demo_sqli.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private Repository repository;

    public boolean login(String username, String password){
        return repository.login(username, password);
    }
}
