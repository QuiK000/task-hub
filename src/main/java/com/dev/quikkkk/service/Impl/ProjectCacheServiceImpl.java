package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.response.ProjectResponse;
import com.dev.quikkkk.entity.Project;
import com.dev.quikkkk.entity.redis.ProjectRedisEntity;
import com.dev.quikkkk.mapper.ProjectMapper;
import com.dev.quikkkk.repository.IProjectRedisRepository;
import com.dev.quikkkk.service.IProjectCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectCacheServiceImpl implements IProjectCacheService {
    private final ProjectMapper projectMapper;
    private final IProjectRedisRepository redisRepository;

    @Value("${app.cache.ttl-seconds}")
    private long timeToLive;

    @Override
    public Optional<ProjectResponse> findInCache(String id) {
        return redisRepository
                .findById(id)
                .map(projectMapper::toProjectResponse);
    }

    @Override
    public void save(Project project) {
        ProjectRedisEntity entity = projectMapper.toProject(project);

        entity.setTimeToLive(timeToLive);
        redisRepository.save(entity);

        log.info("Saved project to cache: {}", project.getId());
    }

    @Override
    public void evict(String id) {
        redisRepository.deleteById(id);
    }
}
