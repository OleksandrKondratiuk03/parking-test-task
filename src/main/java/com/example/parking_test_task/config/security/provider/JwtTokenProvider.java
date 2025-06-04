package com.example.parking_test_task.config.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.parking_test_task.user.entity.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt-secret-key}")
    private String secretKey;

    @Value("${token.expiration.msec}")
    private long tokenExpirationTimeMs;

    @Value("${local-issuer}")
    private String issuer;

    public String createToken(String username, UserRole userRole){

        Date now = new Date();
        Date validateDate = new Date(now.getTime()+tokenExpirationTimeMs);

        return JWT.create()
                .withExpiresAt(validateDate)
                .withIssuedAt(now)
                .withClaim("username",username)
                .withClaim("role", userRole.name())
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(secretKey));
    }

}
