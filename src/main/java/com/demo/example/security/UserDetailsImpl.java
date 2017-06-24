package com.demo.example.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Data
public class UserDetailsImpl implements UserDetails {

    private final Long id;

    private final String username;

    private final String password;

    private final String displayName;

    private final Date lastPasswordResetDate;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(
            Long id,
            String username,
            String password,
            String displayName,
            Date lastPasswordResetDate,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.authorities = authorities;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

