package com.dev.quikkkk.controller;

import com.dev.quikkkk.dto.request.CreateProjectRequest;
import com.dev.quikkkk.dto.request.UpdateProjectRequest;
import com.dev.quikkkk.dto.response.ApiResponse;
import com.dev.quikkkk.dto.response.ProjectResponse;
import com.dev.quikkkk.security.UserPrincipal;
import com.dev.quikkkk.service.IProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Tag(name = "Project controller", description = "Project API")
public class ProjectController {
    private final IProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @RequestBody @Valid CreateProjectRequest request,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        var project = projectService.createProject(request, user.id());
        return ResponseEntity.ok(ApiResponse.ok(project));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects() {
        return ResponseEntity.ok(ApiResponse.ok(projectService.getAllProjects()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@projectSecurityServiceImpl.isProjectOwner(#id)")
    public ResponseEntity<ApiResponse<Optional<ProjectResponse>>> getProjectById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(ApiResponse.ok(projectService.getProjectById(id)));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@projectSecurityServiceImpl.isProjectOwner(#id)")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable String id,
            @RequestBody @Valid UpdateProjectRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(projectService.updateProject(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@projectSecurityServiceImpl.isProjectOwner(#id)")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
            @PathVariable String id
    ) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
