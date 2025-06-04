package com.example.parking_test_task.user.service.impl;

import com.example.parking_test_task.config.security.provider.JwtTokenProvider;
import com.example.parking_test_task.user.entity.User;
import com.example.parking_test_task.user.entity.dto.LoginRequestDto;
import com.example.parking_test_task.user.entity.dto.LoginResponseDto;
import com.example.parking_test_task.user.repo.UserRepository;
import com.example.parking_test_task.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        if (loginRequestDto.getPassword() == null || loginRequestDto.getUsername() == null) {
            log.error("All fields are required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ALL_FIELDS_REQUIRED");
        }

        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found with username " + loginRequestDto.getUsername());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
                });

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            log.error("Password does not match");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "PASSWORD_DOES_NOT_MATCH");
        }

        String token = jwtTokenProvider.createToken(loginRequestDto.getUsername(), user.getRole());

        return new LoginResponseDto(token);
    }

}
