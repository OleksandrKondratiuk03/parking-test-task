package com.example.parking_test_task.user.service;

import com.example.parking_test_task.config.security.dto.JwtUser;
import com.example.parking_test_task.user.entity.dto.SuperAdminDto;
import com.example.parking_test_task.user.entity.dto.UserRequestDto;

public interface UserService {
    void createUser(JwtUser jwtUser, UserRequestDto userRequestDto);

    void createSuperAdmin(SuperAdminDto userRequestDto);
}
