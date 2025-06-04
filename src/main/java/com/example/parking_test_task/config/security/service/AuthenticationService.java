package com.example.parking_test_task.config.security.service;

import com.example.parking_test_task.config.security.dto.JwtUser;

public interface AuthenticationService {
    boolean supports(String token);

    JwtUser authenticate(String token);
}
