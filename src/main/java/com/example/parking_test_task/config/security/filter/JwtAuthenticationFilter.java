package com.example.parking_test_task.config.security.filter;

import com.example.parking_test_task.config.security.dto.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager  authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!authorizationHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        var jwt = authorizationHeader.substring(TOKEN_PREFIX.length());

        try {
            Authentication authenticationResult = authenticationManager.authenticate(new JwtAuthenticationToken(jwt));
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            filterChain.doFilter(request, response);
        }catch (ResponseStatusException e){
            response.sendError(e.getStatusCode().value(), e.getReason());
        }
    }
}
