package com.example.parking_test_task.config.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.parking_test_task.config.security.dto.JwtAuthenticationToken;
import com.example.parking_test_task.config.security.dto.JwtUser;
import com.example.parking_test_task.config.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final List<AuthenticationService> authenticationService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        String issuer = getTokenIssuer(token);

        for (AuthenticationService service : authenticationService){
            if(service.supports(issuer)){
                JwtUser user = service.authenticate(token);
                return new JwtAuthenticationToken(token, user);
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_ISSUER");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }

    private String getTokenIssuer(String token) {
        try {
            var jwt = JWT.decode(token);
            if (jwt.getExpiresAtAsInstant().isBefore(Instant.now())) {
                throw new IllegalStateException();
            }
            return Optional.ofNullable(jwt.getIssuer()).orElseThrow(NullPointerException::new);
        } catch (JWTDecodeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_BEARER_TOKEN");
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ISSUER_CLAIM_MISSING");
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "BEARER_TOKEN_EXPIRED");
        }
    }
}
