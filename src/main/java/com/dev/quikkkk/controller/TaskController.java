package com.dev.quikkkk.controller;

import com.dev.quikkkk.dto.request.CreateTaskRequest;
import com.dev.quikkkk.dto.request.UpdateTaskRequest;
import com.dev.quikkkk.dto.response.ApiResponse;
import com.dev.quikkkk.dto.response.TaskResponse;
import com.dev.quikkkk.service.ITaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Task controller", description = "Task API")
public class TaskController {
    private final ITaskService taskService;

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @RequestBody @Valid CreateTaskRequest request,
            @PathVariable String projectId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(taskService.createTask(request, projectId)));
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks(
            @PathVariable String projectId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(taskService.getAllTasksByProjectId(projectId)));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<Optional<TaskResponse>>> getTaskById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(ApiResponse.ok(taskService.getTaskById(id)));
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable String id,
            @RequestBody @Valid UpdateTaskRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(taskService.updateTask(id, request)));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(
            @PathVariable String id
    ) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
