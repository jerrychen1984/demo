package com.demo.example.data.service;


import com.demo.example.data.service.exception.*;
import com.demo.example.controller.ro.UserRegistry;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {


    void register(UserRegistry user) throws UserNameExistsException
            , InviteCodeNotFoundException
            , InviteCodeWasUsedException
            , InvalidParamsException;

    String login(String username, String password) throws EmailWasNotVerifiedException;

    String refresh(String bearer);
}

