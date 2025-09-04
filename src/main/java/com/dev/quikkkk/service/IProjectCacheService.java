package com.dev.quikkkk.service;

import com.dev.quikkkk.dto.response.ProjectResponse;
import com.dev.quikkkk.entity.Project;

import java.util.Optional;

public interface IProjectCacheService {
    Optional<ProjectResponse> findInCache(String id);

    void save(Project project);

    void evict(String id);
}
