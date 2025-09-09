package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.response.TaskResponse;
import com.dev.quikkkk.entity.Task;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.exception.ErrorCode;
import com.dev.quikkkk.mapper.TaskMapper;
import com.dev.quikkkk.repository.ITaskRepository;
import com.dev.quikkkk.service.ITaskCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskCacheServiceImpl implements ITaskCacheService {
    private final ITaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Cacheable(value = "tasks", key = "#id")
    public Optional<TaskResponse> getCachedTaskById(String id) {
        log.info("Cache MISS for task id={}, Querying DB...", id);
        Task entity = taskRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_NOT_FOUND));
        return Optional.of(taskMapper.toTaskResponse(entity));
    }
}
