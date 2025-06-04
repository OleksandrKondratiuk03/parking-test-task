package com.example.parking_test_task.user.service.impl;

import com.example.parking_test_task.config.security.dto.JwtUser;
import com.example.parking_test_task.user.entity.User;
import com.example.parking_test_task.user.entity.dto.SuperAdminDto;
import com.example.parking_test_task.user.entity.dto.UserRequestDto;
import com.example.parking_test_task.user.entity.enums.UserRole;
import com.example.parking_test_task.user.repo.UserRepository;
import com.example.parking_test_task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(JwtUser jwtUser, UserRequestDto userRequestDto) {

        if(userRequestDto.getUserRole() == null || userRequestDto.getPassword() == null || userRequestDto.getUsername() == null){
            log.error("All fields are required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ALL_FIELDS_REQUIRED");
        }

        User user = new User();
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        if (UserRole.ADMIN.equals(userRequestDto.getUserRole())) {
            if (UserRole.ADMIN.equals(jwtUser.getRole())) {
                log.error("Only super admin can create admins");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER_ROLE_MUST_BE_USER");
            } else if (UserRole.SUPER_ADMIN.equals(jwtUser.getRole())) {
                user.setRole(userRequestDto.getUserRole());
            }
        } else if (UserRole.USER.equals(userRequestDto.getUserRole())) {
            user.setRole(userRequestDto.getUserRole());
        } else {
            log.error("No user can create super admin");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CAN`T_CREATE_SUPER_ADMIN");
        }

        userRepository.save(user);

        log.info("User saved successfully");
    }

    @Override
    public void createSuperAdmin(SuperAdminDto userRequestDto) {

        if(userRequestDto.getPassword() == null || userRequestDto.getUsername() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ALL_FIELDS_REQUIRED");
        }

        User user = User.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(UserRole.SUPER_ADMIN)
                .build();

        userRepository.save(user);
    }
}
