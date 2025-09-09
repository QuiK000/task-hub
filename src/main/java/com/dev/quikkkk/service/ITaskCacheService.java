package com.dev.quikkkk.service;

import com.dev.quikkkk.dto.response.TaskResponse;

import java.util.Optional;

public interface ITaskCacheService {
    Optional<TaskResponse> getCachedTaskById(String id);
}
