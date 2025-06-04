package com.example.parking_test_task.user.service;

import com.example.parking_test_task.user.entity.dto.LoginRequestDto;
import com.example.parking_test_task.user.entity.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);

}
