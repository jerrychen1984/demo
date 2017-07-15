package com.demo.example.data.service;

import com.demo.example.data.cache.JwtTokenCache;
import com.demo.example.data.service.exception.*;
import com.demo.example.controller.ro.UserRegistryRO;
import com.demo.example.data.po.Authority;
import com.demo.example.data.po.InviteCode;
import com.demo.example.data.po.User;
import com.demo.example.data.repository.Repository;
import com.demo.example.security.jwt.JwtTokenUtils;
import com.demo.example.utils.ObjectValidator;
import org.nutz.dao.Cnd;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.FieldMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AuthServiceImpl implements AuthService {

    // Http request header
    private static final String AUTHORIZATION = "Authorization";
    // Authorization value head
    private static final String BEARER = "Bearer ";

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
    @Autowired
    private JwtTokenCache jwtTokenCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegistryRO registry) throws UserNameExistsException
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
    public String login(String username, String password) throws EmailWasNotVerifiedException {

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = authenticationManager.authenticate(upToken);

        final User user = repository.fetch(User.class, Cnd.where("username", "=", username));
        if (!user.isEmailVerified()) {
            throw new EmailWasNotVerifiedException();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenCache.get(username);
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        List<Authority> authorities = repository.query(Authority.class, Cnd.where("user_id","=",user.getId()));
        token = jwtTokenUtils.generateToken(username, authorities.stream()
                .map(e -> new SimpleGrantedAuthority(e.getRole())).collect(toList()));
        if (token != null) {
            jwtTokenCache.put(username, token, jwtTokenUtils.getExpirationDateFromToken(token));
        }

        return token;
    }

    @Override
    public String refresh(String bearer) {
        final String authToken = bearer.substring(BEARER.length());
        final String username = jwtTokenUtils.getUsernameFromToken(authToken);
        final String cachedToken = jwtTokenCache.get(username);
        if (cachedToken != null && cachedToken.equals(authToken)){
            String refreshedToken = jwtTokenUtils.refreshToken(authToken);
            if (refreshedToken != null) {
                jwtTokenCache.put(username, refreshedToken, jwtTokenUtils.getExpirationDateFromToken(refreshedToken));
                return refreshedToken;
            }
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


