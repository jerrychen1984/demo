package com.demo.example.data.service;

import com.demo.example.data.service.exception.InvalidParamsException;
import com.demo.example.controller.ro.UserRegistry;
import com.demo.example.data.po.Authority;
import com.demo.example.data.po.InviteCode;
import com.demo.example.data.po.User;
import com.demo.example.data.repository.Repository;
import com.demo.example.data.service.exception.InviteCodeNotFoundException;
import com.demo.example.data.service.exception.InviteCodeWasUsedException;
import com.demo.example.data.service.exception.UserNameExistsException;
import com.demo.example.security.UserDetailsImpl;
import com.demo.example.security.jwt.JwtTokenUtils;
import com.demo.example.utils.ObjectValidator;
import org.nutz.dao.Cnd;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.FieldMatcher;
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
import org.springframework.util.StringUtils;

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
    @Autowired
    private ObjectValidator objectValidator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegistry registry) throws UserNameExistsException
            , InviteCodeNotFoundException
            , InviteCodeWasUsedException
            , InvalidParamsException {

        objectValidator.check(registry);

        final String username = registry.getUsername();

        User user = repository.fetch(User.class, Cnd.where("username", "=", username));
        if(user != null) {
            throw  new UserNameExistsException();
        }

        final String code = registry.getInviteCode();
        final InviteCode invite = StringUtils.isEmpty(code) ? null
                : repository.fetch(InviteCode.class, Cnd.where("invite_code", "=", code));
        checkoutInviteCode(code, invite);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = registry.getPassword();

        user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(rawPassword));
        user.setDisplayName(registry.getDisplayName());
        user.setLastPasswordResetDate(new Date());

        user = repository.insert(user);

        Authority role = new Authority(user.getId(), "ROLE_USER");
        repository.insert(role);

        if (invite != null) {
            invite.setUsedUserId(user.getId());
            repository.updateWithVersion(code, FieldFilter.create(InviteCode.class
                    , FieldMatcher.make("usedUserId", null, true)));
        }
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

    private void checkoutInviteCode(final String code, final InviteCode invite)
            throws InviteCodeNotFoundException, InviteCodeWasUsedException {

        if (!StringUtils.isEmpty(code)) {
            if (null == invite) {
                throw new InviteCodeNotFoundException();
            }

            if (invite.getUsedUserId() > 0) {
                throw new InviteCodeWasUsedException();
            }
        }
    }
}


