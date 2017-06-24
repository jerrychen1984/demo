package com.demo.example.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRegistry {

    @NotNull
    private String username; // email
    @NotNull
    private String password;
    @NotNull
    private String displayName;

    private String inviteCode;

}

