package com.example.parking_test_task.config.security.dto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthenticationToken implements Authentication {

    private final String token;
    private final JwtUser jwtUser;

    public JwtAuthenticationToken(String jwt) {
        this.token = jwt;
        this.jwtUser = null;
    }

    public JwtAuthenticationToken(String token, JwtUser jwtUser) {
        this.token = token;
        this.jwtUser = jwtUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (jwtUser != null) {
            return List.of(new SimpleGrantedAuthority("ROLE_"+jwtUser.getRole().name()));
        }else {
            return List.of();
        }
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return jwtUser;
    }

    @Override
    public boolean isAuthenticated() {
        return jwtUser != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw  new UnsupportedOperationException("Not supported");
    }

    @Override
    public String getName() {
        if (jwtUser == null) {
            throw new UnsupportedOperationException("User is not authenticated");
        }
        return jwtUser.getUsername();
    }
}
