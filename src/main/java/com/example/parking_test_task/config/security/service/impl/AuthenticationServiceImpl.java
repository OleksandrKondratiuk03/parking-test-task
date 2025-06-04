package com.example.parking_test_task.config.security.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.parking_test_task.config.security.dto.JwtUser;
import com.example.parking_test_task.config.security.service.AuthenticationService;
import com.example.parking_test_task.user.entity.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${jwt-secret-key}")
    private String secretKey;

    @Value("${local-issuer}")
    private String issuer;

    @Override
    public boolean supports(String issuer) {
        return this.issuer.equals(issuer);
    }

    @Override
    public JwtUser authenticate(String token) {
        DecodedJWT jwt = decodeJWT(token);
        String username = jwt.getClaim("username").asString();
        String role = jwt.getClaim("role").asString();
        var issuer = Optional.ofNullable(jwt.getIssuer())
                .orElseThrow(() -> new NoSuchElementException("ISSUER"));
        return JwtUser.builder().username(username).issuer(issuer).role(UserRole.valueOf(role)).build();
    }

    private DecodedJWT decodeJWT(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
    }
}
