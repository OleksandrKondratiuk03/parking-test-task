package com.example.parking_test_task.config.security.dto;

import com.example.parking_test_task.user.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUser {
    private String token;
    private String username;
    private UserRole role;
    private String issuer;
}
