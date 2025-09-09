package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.request.CreateTaskRequest;
import com.dev.quikkkk.dto.request.UpdateTaskRequest;
import com.dev.quikkkk.dto.response.TaskResponse;
import com.dev.quikkkk.entity.Task;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.mapper.TaskMapper;
import com.dev.quikkkk.repository.IProjectRepository;
import com.dev.quikkkk.repository.ITaskRepository;
import com.dev.quikkkk.service.ITaskCacheService;
import com.dev.quikkkk.service.ITaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.dev.quikkkk.exception.ErrorCode.PROJECT_NOT_FOUND;
import static com.dev.quikkkk.exception.ErrorCode.TASK_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements ITaskService {
    private final ITaskRepository taskRepository;
    private final ITaskCacheService cacheService;
    private final IProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskResponse createTask(CreateTaskRequest request, String projectId) {
        var project = projectRepository.findById(projectId).orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));
        var task = taskMapper.toTask(request, project);
        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    @Override
    public List<TaskResponse> getAllTasksByProjectId(String projectId) {
        var project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));

        List<String> taskIds = taskRepository
                .findAllByProjectId(project)
                .stream()
                .map(Task::getId)
                .toList();

        return taskIds
                .stream()
                .map(taskId -> getTaskById(taskId).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Optional<TaskResponse> getTaskById(String id) {
        return cacheService.getCachedTaskById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "tasks", allEntries = true),
            @CacheEvict(value = "task", key = "#id")
    })
    public TaskResponse updateTask(String id, UpdateTaskRequest request) {
        Task task = validateTask(id);

        taskMapper.mergeTask(task, request);
        Task taskResponse = taskRepository.save(task);

        return taskMapper.toTaskResponse(taskResponse);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "tasks", allEntries = true),
            @CacheEvict(value = "task", key = "#id")
    })
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    private Task validateTask(String taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new BusinessException(TASK_NOT_FOUND));
    }
}
