package com.example.parking_test_task.user;

import com.example.parking_test_task.config.security.provider.JwtTokenProvider;
import com.example.parking_test_task.user.entity.User;
import com.example.parking_test_task.user.entity.dto.LoginRequestDto;
import com.example.parking_test_task.user.entity.dto.LoginResponseDto;
import com.example.parking_test_task.user.entity.enums.UserRole;
import com.example.parking_test_task.user.repo.UserRepository;
import com.example.parking_test_task.user.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private  JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;


    @Test
    void loginTest_BadRequest(){
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername(null);
        loginRequestDto.setPassword("pass");

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> authService.login(loginRequestDto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("ALL_FIELDS_REQUIRED", exception.getReason());
    }

    @Test
    void loginTest_PassNotMatching(){
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("user");
        loginRequestDto.setPassword("pass");

        User user = User.builder()
                .id(1L)
                .role(UserRole.USER)
                .username("user")
                .password("notPass")
                .build();

        when(passwordEncoder.matches(any(),any())).thenReturn(false);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> authService.login(loginRequestDto));


        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("PASSWORD_DOES_NOT_MATCH", exception.getReason());

        verify(passwordEncoder, times(1)).matches(any(),any());
        verify(userRepository, times(1)).findByUsername(any());
    }

    @Test
    void loginTest_Success(){
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("user");
        loginRequestDto.setPassword("pass");

        User user = User.builder()
                .id(1L)
                .role(UserRole.USER)
                .username("user")
                .password("notPass")
                .build();

        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(jwtTokenProvider.createToken(any(),any())).thenReturn("token");

        LoginResponseDto result =  authService.login(loginRequestDto);

        assertEquals("token", result.getToken());

        verify(passwordEncoder, times(1)).matches(any(),any());
        verify(userRepository, times(1)).findByUsername(any());
        verify(jwtTokenProvider, times(1)).createToken(any(),any());
    }
}
