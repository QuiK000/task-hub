package com.dev.quikkkk.service.impl;

import com.dev.quikkkk.dto.request.LoginRequest;
import com.dev.quikkkk.dto.request.RegistrationRequest;
import com.dev.quikkkk.dto.response.AuthenticationResponse;
import com.dev.quikkkk.entity.Role;
import com.dev.quikkkk.entity.User;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.mapper.UserMapper;
import com.dev.quikkkk.repository.IRoleRepository;
import com.dev.quikkkk.repository.IUserRepository;
import com.dev.quikkkk.service.IJwtService;
import com.dev.quikkkk.service.Impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Authentication Service test")
public class AuthenticationServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private IJwtService jwtService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    @DisplayName("Should successfully login with valid credentials")
    void shouldSuccessfullyLoginWithValidCredentials() {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        User user = User.builder()
                .id("user-id")
                .username("testuser")
                .email("test@example.com")
                .build();

        when(userRepository.findByUsernameIgnoreCase("testuser")).thenReturn(Optional.of(user));
        when(jwtService.generateAccessToken(user)).thenReturn("access-token");
        when(jwtService.generateRefreshToken(user)).thenReturn("refresh-token");

        AuthenticationResponse response = authenticationService.login(loginRequest);

        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
        assertEquals("Bearer", response.getTokenType());

        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsernameIgnoreCase("testuser");
        verify(jwtService).generateAccessToken(user);
        verify(jwtService).generateRefreshToken(user);
    }

    @Test
    @DisplayName("Should successfully register new user")
    void shouldSuccessfullyRegisterNewUser() {
        RegistrationRequest request = RegistrationRequest.builder()
                .username("newuser")
                .email("new@example.com")
                .password("Password123!")
                .confirmPassword("Password123!")
                .build();

        Role userRole = Role.builder()
                .id("role-id")
                .name("ROLE_USER")
                .build();

        User mappedUser = User.builder()
                .username("newuser")
                .email("new@example.com")
                .build();

        when(userRepository.existsByUsernameIgnoreCase("newuser")).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase("new@example.com")).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(userMapper.toUser(request)).thenReturn(mappedUser);

        assertDoesNotThrow(() -> authenticationService.register(request));

        verify(userRepository).existsByUsernameIgnoreCase("newuser");
        verify(userRepository).existsByEmailIgnoreCase("new@example.com");
        verify(roleRepository).findByName("ROLE_USER");
        verify(userMapper).toUser(request);
        verify(userRepository).save(mappedUser);
    }

    @DisplayName("Should throw exception when username already exists")
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        RegistrationRequest request = RegistrationRequest.builder()
                .username("existinguser")
                .email("new@example.com")
                .password("Password123!")
                .confirmPassword("Password123!")
                .build();

        when(userRepository.existsByUsernameIgnoreCase("existinguser")).thenReturn(true);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> authenticationService.register(request)
        );

        assertEquals("USERNAME_ALREADY_EXISTS", exception.getErrorCode().getCode());
        verify(userRepository).existsByUsernameIgnoreCase("existinguser");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when passwords don't match")
    void shouldThrowExceptionWhenPasswordsDontMatch() {
        RegistrationRequest request = RegistrationRequest.builder()
                .username("newuser")
                .email("new@example.com")
                .password("Password123!")
                .confirmPassword("DifferentPassword!")
                .build();

        when(userRepository.existsByUsernameIgnoreCase("newuser")).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase("new@example.com")).thenReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> authenticationService.register(request)
        );

        assertEquals("PASSWORD_MISMATCH", exception.getErrorCode().getCode());
    }
}
