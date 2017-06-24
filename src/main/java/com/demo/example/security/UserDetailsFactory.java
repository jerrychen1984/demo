package com.demo.example.security;

import com.demo.example.data.po.Authority;
import com.demo.example.data.po.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public final class UserDetailsFactory {

    public static UserDetailsImpl create(User user, List<Authority> authorities) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getDisplayName(),
                user.getLastPasswordResetDate(),
                mapToGrantedAuthorities(authorities)
        );
    }

    public static String joinAuthority(Collection<? extends GrantedAuthority> authorities) {
        String[] roles = authorities.stream()
                .map(GrantedAuthority::getAuthority).collect(toList())
                .toArray(new String[0]);

        return StringUtils.join(roles, ",");
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(e -> new SimpleGrantedAuthority(e.getRole()))
                .collect(toList());
    }

}


