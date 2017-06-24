package com.demo.example.data.service;

import com.demo.example.controller.ro.UserRegistry;
import com.demo.example.data.po.Authority;
import com.demo.example.data.po.User;
import com.demo.example.data.repository.Repository;
import com.demo.example.security.UserDetailsImpl;
import com.demo.example.security.jwt.JwtTokenUtils;
import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    // Http request header
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private Repository repository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(UserRegistry registry) {
        final String username = registry.getUsername();

        User user = repository.fetch(User.class, Cnd.where("username", "=", username));
        if(user != null) {
            return null;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = registry.getPassword();

        user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(rawPassword));
        user.setEmail(registry.getEmail());
        user.setLastPasswordResetDate(new Date());

        user = repository.insert(user);

        Authority role = new Authority(user.getId(), "ROLE_USER");
        repository.insert(role);

        return user;
    }

    @Override
    public String login(String username, String password) {

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtils.generateToken(userDetails);
    }

    @Override
    public String refresh(String bearer) {
        final String token = bearer.substring(AUTHORIZATION.length());
        String username = jwtTokenUtils.getUsernameFromToken(token);
        UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtils.refreshToken(token);
        }

        return null;
    }
}


