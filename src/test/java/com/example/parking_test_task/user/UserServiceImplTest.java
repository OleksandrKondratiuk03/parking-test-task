package com.example.parking_test_task.user;

import com.example.parking_test_task.config.security.dto.JwtUser;
import com.example.parking_test_task.user.entity.dto.UserRequestDto;
import com.example.parking_test_task.user.entity.enums.UserRole;
import com.example.parking_test_task.user.repo.UserRepository;
import com.example.parking_test_task.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserTest_BadRequest() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserRole(UserRole.USER);
        userRequestDto.setUsername(null);
        userRequestDto.setPassword("pass");

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> userService.createUser(new JwtUser(), userRequestDto));


        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("ALL_FIELDS_REQUIRED", exception.getReason());
    }

    @Test
    void createUserTest_AdminAdmin() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserRole(UserRole.ADMIN);
        userRequestDto.setUsername("user");
        userRequestDto.setPassword("pass");

        JwtUser jwtUser = JwtUser.builder()
                .role(UserRole.ADMIN)
                .build();

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> userService.createUser(jwtUser, userRequestDto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("USER_ROLE_MUST_BE_USER", exception.getReason());
    }

    @Test
    void createUserTest_SuperAdminAdmin() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserRole(UserRole.ADMIN);
        userRequestDto.setUsername("user");
        userRequestDto.setPassword("pass");

        JwtUser jwtUser = JwtUser.builder()
                .role(UserRole.SUPER_ADMIN)
                .build();

        userService.createUser(jwtUser, userRequestDto);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void createUserTest_SuperAdmin() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserRole(UserRole.SUPER_ADMIN);
        userRequestDto.setUsername("user");
        userRequestDto.setPassword("pass");

        JwtUser jwtUser = JwtUser.builder()
                .role(UserRole.ADMIN)
                .build();

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> userService.createUser(jwtUser, userRequestDto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("CAN`T_CREATE_SUPER_ADMIN", exception.getReason());
    }

    @Test
    void createUserTest_AdminUser() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserRole(UserRole.USER);
        userRequestDto.setUsername("user");
        userRequestDto.setPassword("pass");

        JwtUser jwtUser = JwtUser.builder()
                .role(UserRole.ADMIN)
                .build();

        userService.createUser(jwtUser, userRequestDto);

        verify(userRepository, times(1)).save(any());
    }


}
