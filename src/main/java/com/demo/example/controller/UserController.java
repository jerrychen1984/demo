package com.demo.example.controller;

import com.demo.example.controller.ro.UserRegistry;
import com.demo.example.controller.vo.ResponseData;
import com.demo.example.data.po.User;
import com.demo.example.data.repository.Repository;
import com.demo.example.data.service.AuthService;
import com.demo.example.data.service.exception.InvalidParamsException;
import com.demo.example.data.service.exception.InviteCodeNotFoundException;
import com.demo.example.data.service.exception.InviteCodeWasUsedException;
import com.demo.example.data.service.exception.UserNameExistsException;
import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Repository repository;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/register"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData<?> registerUser(@RequestBody UserRegistry registry) {

        try {
            authService.register(registry);
        } catch ( UserNameExistsException
                | InviteCodeNotFoundException
                | InviteCodeWasUsedException
                | InvalidParamsException e) {
            return ResponseData.error(e.getMessage());
        } catch (Exception e) {
            return ResponseData.error("未知错误");
        }

        return ResponseData.success(true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Iterable<User> getUsers() {

        return repository.query(User.class, Cnd.NEW());

    }

}





