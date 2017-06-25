package com.demo.example.data.service;

import com.demo.example.data.po.User;

public interface EmailService {

    boolean sendVerifyEmail(User user, String link);

}


