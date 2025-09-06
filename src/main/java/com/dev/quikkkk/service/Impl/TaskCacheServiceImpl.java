package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.response.TaskResponse;
import com.dev.quikkkk.entity.Task;
import com.dev.quikkkk.entity.redis.TaskRedisEntity;
import com.dev.quikkkk.mapper.TaskMapper;
import com.dev.quikkkk.repository.ITaskRedisRepository;
import com.dev.quikkkk.service.ITaskCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskCacheServiceImpl implements ITaskCacheService {
    private final ITaskRedisRepository taskRedisRepository;
    private final TaskMapper taskMapper;

    @Value("${app.cache.ttl-seconds}")
    private long timeToLive;

    @Override
    public Optional<TaskResponse> findInCache(String id) {
        return taskRedisRepository
                .findById(id)
                .map(taskMapper::toTaskResponse);
    }

    @Override
    public void save(Task task) {
        TaskRedisEntity entity = taskMapper.toTask(task);
        entity.setTimeToLive(timeToLive);
        taskRedisRepository.save(entity);

        log.info("Saved task to cache: {}", task.getId());
    }

    @Override
    public void evict(String id) {
        log.info("Evicted task from cache: {}", id);
        taskRedisRepository.deleteById(id);
    }
}
