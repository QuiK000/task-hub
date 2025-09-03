package com.dev.quikkkk.controller;

import com.dev.quikkkk.dto.request.LoginRequest;
import com.dev.quikkkk.dto.request.RefreshRequest;
import com.dev.quikkkk.dto.request.RegistrationRequest;
import com.dev.quikkkk.dto.response.ApiResponse;
import com.dev.quikkkk.dto.response.AuthenticationResponse;
import com.dev.quikkkk.service.IAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication controller", description = "Authentication API")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody RegistrationRequest request
    ) {
        authenticationService.register(request);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(authenticationService.login(request)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(
            @RequestBody RefreshRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(authenticationService.refreshToken(request)));
    }
}
