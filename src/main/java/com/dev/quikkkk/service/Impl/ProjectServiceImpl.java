package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.request.CreateProjectRequest;
import com.dev.quikkkk.dto.request.UpdateProjectRequest;
import com.dev.quikkkk.dto.response.ProjectResponse;
import com.dev.quikkkk.entity.Project;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.mapper.ProjectMapper;
import com.dev.quikkkk.repository.IProjectRepository;
import com.dev.quikkkk.repository.IUserRepository;
import com.dev.quikkkk.service.IProjectCacheService;
import com.dev.quikkkk.service.IProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final IProjectCacheService projectCacheService;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponse createProject(CreateProjectRequest request, String ownerId) {
        var user = userRepository.findById(ownerId).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
        var project = projectMapper.toProject(request, user);
        var savedProject = projectRepository.save(project);

        return projectMapper.toProjectResponse(savedProject);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository
                .findAll()
                .stream()
                .map(projectMapper::toProjectResponse)
                .toList();
    }

    @Override
    public Optional<ProjectResponse> getProjectById(String id) {
        Optional<ProjectResponse> cachedProject = projectCacheService.findInCache(id);
        if (cachedProject.isPresent()) return cachedProject;

        log.info("Cache MISS for id={}", id);

        Project entity = projectRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));

        projectCacheService.save(entity);
        return Optional.of(projectMapper.toProjectResponse(entity));
    }

    @Override
    public ProjectResponse updateProject(String id, UpdateProjectRequest request) {
        return null;
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
        projectCacheService.evict(id);
    }
}
