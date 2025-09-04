package com.dev.quikkkk.mapper;

import com.dev.quikkkk.dto.request.CreateProjectRequest;
import com.dev.quikkkk.dto.response.ProjectResponse;
import com.dev.quikkkk.entity.Project;
import com.dev.quikkkk.entity.User;
import com.dev.quikkkk.entity.redis.ProjectRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMapper {

    public Project toProject(CreateProjectRequest request, User user) {
        return Project
                .builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .ownerId(user)
                .build();
    }

    public ProjectRedisEntity toProject(Project project) {
        return ProjectRedisEntity
                .builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .ownerId(project.getOwnerId().getId())
                .build();
    }

    public ProjectResponse toProjectResponse(Project project) {
        return ProjectResponse
                .builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .ownerId(project.getOwnerId().getId())
                .createdDate(project.getCreatedDate())
                .build();
    }

    public ProjectResponse toProjectResponse(ProjectRedisEntity project) {
        return ProjectResponse
                .builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .ownerId(project.getOwnerId())
                .createdDate(project.getCreatedDate())
                .build();
    }
}
