package com.demo.example.data.service;


import com.demo.example.data.service.exception.InvalidParamsException;
import com.demo.example.controller.ro.UserRegistry;
import com.demo.example.data.service.exception.InviteCodeNotFoundException;
import com.demo.example.data.service.exception.InviteCodeWasUsedException;
import com.demo.example.data.service.exception.UserNameExistsException;

public interface AuthService {

    void register(UserRegistry user) throws UserNameExistsException
            , InviteCodeNotFoundException
            , InviteCodeWasUsedException
            , InvalidParamsException;

    String login(String username, String password);

    String refresh(String bearer);
}

