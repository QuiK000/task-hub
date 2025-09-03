package com.dev.quikkkk.service;

import com.dev.quikkkk.dto.response.UserResponse;
import com.dev.quikkkk.entity.User;

import java.util.Optional;

public interface IUserCacheService {
    Optional<UserResponse> findInCache(String id);

    void save(User user);

    void evict(String id);
}
