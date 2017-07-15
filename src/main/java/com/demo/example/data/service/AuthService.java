package com.demo.example.data.service;


import com.demo.example.data.service.exception.*;
import com.demo.example.controller.ro.UserRegistryRO;

public interface AuthService {


    void register(UserRegistryRO user) throws UserNameExistsException
            , InviteCodeNotFoundException
            , InviteCodeWasUsedException
            , InvalidParamsException;

    String login(String username, String password) throws EmailWasNotVerifiedException;

    String refresh(String bearer);
}

