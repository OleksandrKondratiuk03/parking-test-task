package com.example.parking_test_task.user.controller;

import com.example.parking_test_task.config.security.dto.JwtUser;
import com.example.parking_test_task.user.entity.dto.SuperAdminDto;
import com.example.parking_test_task.user.entity.dto.UserRequestDto;
import com.example.parking_test_task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/super-admin")
    public ResponseEntity<Void> createSuperAdmin( @RequestBody SuperAdminDto userRequestDto){
        userService.createSuperAdmin(userRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Void> createNewUser(@AuthenticationPrincipal JwtUser jwtUser, @RequestBody UserRequestDto userRequestDto){
        userService.createUser(jwtUser,userRequestDto);
        return ResponseEntity.ok().build();
    }
}
