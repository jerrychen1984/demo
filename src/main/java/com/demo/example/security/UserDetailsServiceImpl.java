package com.demo.example.security;

import com.demo.example.data.po.Authority;
import com.demo.example.data.po.User;
import com.demo.example.data.repository.Repository;
import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private Repository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.fetch(User.class, Cnd.where("username", "=", username));
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            List<Authority> authorities = repository.query(Authority.class
                    , Cnd.where("user_id", "=", user.getId()));
            return UserDetailsFactory.create(user, authorities);
        }
    }
}



