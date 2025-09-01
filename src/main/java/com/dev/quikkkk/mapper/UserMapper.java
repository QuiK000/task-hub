package com.dev.quikkkk.mapper;

import com.dev.quikkkk.dto.request.RegistrationRequest;
import com.dev.quikkkk.dto.response.UserResponse;
import com.dev.quikkkk.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User toUser(RegistrationRequest request) {
        return User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .locked(false)
                .expired(false)
                .emailVerified(false)
                .roles(new HashSet<>())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
