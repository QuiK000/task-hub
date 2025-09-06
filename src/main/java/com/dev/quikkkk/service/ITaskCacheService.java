package com.dev.quikkkk.service;

import com.dev.quikkkk.dto.response.TaskResponse;
import com.dev.quikkkk.entity.Task;

import java.util.Optional;

public interface ITaskCacheService {
    Optional<TaskResponse> findInCache(String id);

    void save(Task task);

    void evict(String id);
}
