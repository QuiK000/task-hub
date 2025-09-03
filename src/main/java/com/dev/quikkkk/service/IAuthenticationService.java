package com.dev.quikkkk.service;

import com.dev.quikkkk.dto.request.LoginRequest;
import com.dev.quikkkk.dto.request.RefreshRequest;
import com.dev.quikkkk.dto.request.RegistrationRequest;
import com.dev.quikkkk.dto.response.AuthenticationResponse;
import com.dev.quikkkk.dto.response.UserResponse;

public interface IAuthenticationService {
    AuthenticationResponse login(LoginRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);

    void register(RegistrationRequest request);

    UserResponse getUserById(String id);
}
