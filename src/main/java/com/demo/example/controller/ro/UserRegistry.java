package com.demo.example.controller.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

@Data
public class UserRegistry {

    @NotNull @Email
    private String username; // email
    @NotNull
    private String password;
    @NotNull
    private String displayName;

    private String inviteCode;

}

