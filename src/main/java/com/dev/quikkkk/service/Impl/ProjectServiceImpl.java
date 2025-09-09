package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.request.CreateProjectRequest;
import com.dev.quikkkk.dto.request.UpdateProjectRequest;
import com.dev.quikkkk.dto.response.ProjectResponse;
import com.dev.quikkkk.entity.Project;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.mapper.ProjectMapper;
import com.dev.quikkkk.repository.IProjectRepository;
import com.dev.quikkkk.repository.IUserRepository;
import com.dev.quikkkk.service.IProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dev.quikkkk.exception.ErrorCode.PROJECT_NOT_FOUND;
import static com.dev.quikkkk.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements IProjectService {
    private final IProjectRepository projectRepository;
    private final IUserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    @CacheEvict(value = "projects", allEntries = true)
    public ProjectResponse createProject(CreateProjectRequest request, String ownerId) {
        var user = userRepository.findById(ownerId).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
        var project = projectMapper.toProject(request, user);
        var savedProject = projectRepository.save(project);

        return projectMapper.toProjectResponse(savedProject);
    }

    @Override
    @Cacheable("projects")
    public List<ProjectResponse> getAllProjects() {
        return projectRepository
                .findAll()
                .stream()
                .map(projectMapper::toProjectResponse)
                .toList();
    }

    @Override
    @Cacheable(value = "projects", key = "#id")
    public Optional<ProjectResponse> getProjectById(String id) {
        log.info("Cache MISS for project id={}. Querying DB...", id);
        Project entity = projectRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));

        return Optional.of(projectMapper.toProjectResponse(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "projects", allEntries = true),
            @CacheEvict(value = "projectById", key = "#id")
    })
    public ProjectResponse updateProject(String id, UpdateProjectRequest request) {
        Project project = validateProject(id);

        projectMapper.mergeProject(project, request);
        Project projectResponse = projectRepository.save(project);

        return projectMapper.toProjectResponse(projectResponse);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "projects", allEntries = true),
            @CacheEvict(value = "projectById", key = "#id")
    })
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    private Project validateProject(String projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));
    }
}
