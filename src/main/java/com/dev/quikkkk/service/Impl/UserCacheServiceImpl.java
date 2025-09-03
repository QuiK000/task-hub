package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.response.UserResponse;
import com.dev.quikkkk.entity.User;
import com.dev.quikkkk.entity.UserRedisEntity;
import com.dev.quikkkk.mapper.UserMapper;
import com.dev.quikkkk.repository.IUserRedisRepository;
import com.dev.quikkkk.service.IUserCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCacheServiceImpl implements IUserCacheService {
    private final IUserRedisRepository userRedisRepository;
    private final UserMapper userMapper;

    @Value("${app.cache.ttl-seconds}")
    private long cacheTtlSeconds;

    @Override
    public Optional<UserResponse> findInCache(String id) {
        return userRedisRepository
                .findById(id)
                .map(userMapper::toUserResponse);
    }

    @Override
    public void save(User user) {
        UserRedisEntity entity = userMapper.toUser(user);

        entity.setTimeToLive(cacheTtlSeconds);
        userRedisRepository.save(entity);

        log.info("Saved user to cache: {}", user.getId());
    }

    @Override
    public void evict(String id) {
        userRedisRepository.deleteById(id);
        log.info("Evicted user from cache: {}", id);
    }
}
