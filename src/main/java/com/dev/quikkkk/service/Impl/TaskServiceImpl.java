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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dev.quikkkk.exception.ErrorCode.PROJECT_NOT_FOUND;
import static com.dev.quikkkk.exception.ErrorCode.TASK_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements ITaskService {
    private final ITaskRepository taskRepository;
    private final IProjectRepository projectRepository;
    private final ITaskCacheService taskCacheService;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponse createTask(CreateTaskRequest request, String projectId) {
        var project = projectRepository.findById(projectId).orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));
        var task = taskMapper.toTask(request, project);
        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    @Override
    public List<TaskResponse> getAllTasksByProjectId(String projectId) {
        var project = projectRepository.findById(projectId).orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));
        return taskRepository
                .findAllByProjectId(project)
                .stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    @Override
    public Optional<TaskResponse> getTaskById(String id) {
        Optional<TaskResponse> cachedTask = taskCacheService.findInCache(id);
        if (cachedTask.isPresent()) return cachedTask;

        log.info("Cache MISS for id={}", id);

        Task entity = taskRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(TASK_NOT_FOUND));

        taskCacheService.save(entity);
        return Optional.of(taskMapper.toTaskResponse(entity));
    }

    @Override
    public TaskResponse updateTask(String id, UpdateTaskRequest request) {
        return null;
    }

    @Override
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
        taskCacheService.evict(id);
    }
}
