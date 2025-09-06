package com.dev.quikkkk.mapper;

import com.dev.quikkkk.dto.request.CreateTaskRequest;
import com.dev.quikkkk.dto.request.UpdateTaskRequest;
import com.dev.quikkkk.dto.response.TaskResponse;
import com.dev.quikkkk.entity.Project;
import com.dev.quikkkk.entity.Task;
import com.dev.quikkkk.entity.User;
import com.dev.quikkkk.entity.common.TaskStatus;
import com.dev.quikkkk.entity.redis.TaskRedisEntity;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.exception.ErrorCode;
import com.dev.quikkkk.repository.IUserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMapper {
    private final IUserRepository userRepository;

    public Task toTask(CreateTaskRequest request, Project project) {
        User assignee = null;

        if (request.getAssigneeId() != null && !request.getAssigneeId().isEmpty()) {
            assignee = userRepository
                    .findById(request.getAssigneeId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        }

        return Task
                .builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.TODO)
                .projectId(project)
                .assigneeId(assignee)
                .build();
    }

    public TaskRedisEntity toTask(Task task) {
        return TaskRedisEntity
                .builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .projectId(task.getProjectId().getId())
                .assigneeId(task.getAssigneeId() != null ? task.getAssigneeId().getId() : null)
                .createdDate(task.getCreatedDate() != null ? task.getCreatedDate().toString() : null)
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        return TaskResponse
                .builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .projectId(task.getProjectId().getId())
                .assigneeId(task.getAssigneeId() != null ? task.getAssigneeId().getId() : null)
                .createdAt(task.getCreatedDate() != null ? task.getCreatedDate().toString() : null)
                .build();
    }

    public TaskResponse toTaskResponse(TaskRedisEntity entity) {
        return TaskResponse
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .projectId(entity.getProjectId())
                .assigneeId(entity.getAssigneeId())
                .createdAt(entity.getCreatedDate())
                .build();
    }

    public void mergeTask(Task task, UpdateTaskRequest request) {
        if (StringUtils.isNotBlank(request.getTitle()) && !request.getTitle().equals(task.getTitle()))
            task.setTitle(request.getTitle());

        if (StringUtils.isNotBlank(request.getDescription()) && !request.getDescription().equals(task.getDescription()))
            task.setDescription(request.getDescription());

        if (request.getAssigneeId() != null) {
            if (request.getAssigneeId().isEmpty()) {
                task.setAssigneeId(null);
            } else {
                User assignee = userRepository
                        .findById(request.getAssigneeId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
                task.setAssigneeId(assignee);
            }
        }
    }
}
