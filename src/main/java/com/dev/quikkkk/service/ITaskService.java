package com.dev.quikkkk.service;

import com.dev.quikkkk.dto.request.CreateTaskRequest;
import com.dev.quikkkk.dto.request.UpdateTaskRequest;
import com.dev.quikkkk.dto.response.TaskResponse;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    TaskResponse createTask(CreateTaskRequest request, String projectId);

    List<TaskResponse> getAllTasksByProjectId(String projectId);

    Optional<TaskResponse> getTaskById(String id);

    TaskResponse updateTask(String id, UpdateTaskRequest request);

    void deleteTask(String id);
}
