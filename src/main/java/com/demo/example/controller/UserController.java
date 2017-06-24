package com.demo.example.controller;

import com.demo.example.data.po.User;
import com.demo.example.data.repository.Repository;
import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private Repository repository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getUsers() {

        return repository.query(User.class, Cnd.NEW());

    }

    @PostAuthorize("returnObject.username == principal.username or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long userId) {
        return repository.fetch(User.class, Cnd.where("id", "=", userId));
    }

    @PostAuthorize("returnObject.username == principal.username or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public User queryByUsername(@RequestParam(value="username") String username) {

        return repository.fetch(User.class, Cnd.where("username", "=", username));

    }
}




