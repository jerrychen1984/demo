package com.demo.example.data.service;


import com.demo.example.controller.ro.UserRegistry;
import com.demo.example.data.po.User;

public interface AuthService {

    User register(UserRegistry user);

    String login(String username, String password);

    String refresh(String bearer);
}

