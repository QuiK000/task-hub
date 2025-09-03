package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.request.UpdateUserRequest;
import com.dev.quikkkk.dto.response.UserResponse;
import com.dev.quikkkk.entity.User;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.mapper.UserMapper;
import com.dev.quikkkk.repository.IUserRepository;
import com.dev.quikkkk.service.IUserCacheService;
import com.dev.quikkkk.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dev.quikkkk.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IUserCacheService userCacheService;
    private final UserMapper userMapper;

    @Override
    public Set<UserResponse> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<UserResponse> getUserById(String id) {
        Optional<UserResponse> cachedUser = userCacheService.findInCache(id);
        if (cachedUser.isPresent()) return cachedUser;

        log.info("Cache MISS for id={}", id);
        User entity = userRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        userCacheService.save(entity);
        return Optional.of(userMapper.toUserResponse(entity));
    }

    @Override
    public UserResponse updateUser(String id, UpdateUserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
        userCacheService.evict(id);
    }
}
