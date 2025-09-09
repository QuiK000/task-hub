package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.request.UpdateUserRequest;
import com.dev.quikkkk.dto.response.UserResponse;
import com.dev.quikkkk.entity.User;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.mapper.UserMapper;
import com.dev.quikkkk.repository.IUserRepository;
import com.dev.quikkkk.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dev.quikkkk.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Cacheable("users")
    public Set<UserResponse> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public Optional<UserResponse> getUserById(String id) {
        log.info("Cache MISS for id={}", id);
        User entity = userRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        return Optional.of(userMapper.toUserResponse(entity));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "userById", key = "#id")
    })
    public UserResponse updateUser(String id, UpdateUserRequest request) {
        return null; // TODO: todo...
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "userById", key = "#id")
    })
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
