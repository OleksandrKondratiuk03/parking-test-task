package com.example.parking_test_task.user.entity.dto;

import com.example.parking_test_task.user.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String username;
    private String password;
    private UserRole userRole;
}
