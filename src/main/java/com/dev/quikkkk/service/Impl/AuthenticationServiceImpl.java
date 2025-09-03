package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.request.LoginRequest;
import com.dev.quikkkk.dto.request.RefreshRequest;
import com.dev.quikkkk.dto.request.RegistrationRequest;
import com.dev.quikkkk.dto.response.AuthenticationResponse;
import com.dev.quikkkk.entity.Role;
import com.dev.quikkkk.entity.User;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.mapper.UserMapper;
import com.dev.quikkkk.repository.IRoleRepository;
import com.dev.quikkkk.repository.IUserRepository;
import com.dev.quikkkk.service.IAuthenticationService;
import com.dev.quikkkk.service.IJwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.dev.quikkkk.exception.ErrorCode.EMAIL_ALREADY_EXISTS;
import static com.dev.quikkkk.exception.ErrorCode.PASSWORD_MISMATCH;
import static com.dev.quikkkk.exception.ErrorCode.USERNAME_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final static String TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByUsernameIgnoreCase(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String token = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse
                .builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(TOKEN_TYPE)
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        String newAccessToken = jwtService.refreshAccessToken(request.getRefreshToken());
        return AuthenticationResponse
                .builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(TOKEN_TYPE)
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUsername(request.getUsername());
        checkUserEmail(request.getEmail());
        checkPasswords(request.getPassword(), request.getConfirmPassword());

        Role defaultRole = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exist"));

        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        User user = userMapper.toUser(request);
        user.setRoles(roles);

        log.debug("Registering user: {}", user);
        userRepository.save(user);

        defaultRole.getUsers().add(user);
        roleRepository.save(defaultRole);
    }

    private void checkUserEmail(String email) {
        boolean emailExists = userRepository.existsByEmailIgnoreCase(email);
        if (emailExists) throw new BusinessException(EMAIL_ALREADY_EXISTS);
    }

    private void checkUsername(String username) {
        boolean usernameExists = userRepository.existsByUsernameIgnoreCase(username);
        if (usernameExists) throw new BusinessException(USERNAME_ALREADY_EXISTS);
    }

    private void checkPasswords(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) throw new BusinessException(PASSWORD_MISMATCH);
    }
}
