package com.dev.quikkkk.service;

import com.dev.quikkkk.dto.request.CreateProjectRequest;
import com.dev.quikkkk.dto.request.UpdateProjectRequest;
import com.dev.quikkkk.dto.response.ProjectResponse;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    ProjectResponse createProject(CreateProjectRequest request, String ownerId);

    List<ProjectResponse> getAllProjects();

    Optional<ProjectResponse> getProjectById(String id);

    ProjectResponse updateProject(String id, UpdateProjectRequest request);

    void deleteProject(String id);
}
