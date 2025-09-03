package com.dev.quikkkk.service;

import com.dev.quikkkk.dto.request.UpdateUserRequest;
import com.dev.quikkkk.dto.response.UserResponse;

import java.util.Optional;
import java.util.Set;

public interface IUserService {
    Set<UserResponse> getAllUsers();

    Optional<UserResponse> getUserById(String id);

    UserResponse updateUser(String id, UpdateUserRequest request);

    void deleteUser(String id);
}
