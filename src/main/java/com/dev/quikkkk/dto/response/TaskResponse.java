package com.dev.quikkkk.dto.response;

import com.dev.quikkkk.entity.common.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse implements Serializable {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private String projectId;
    private String assigneeId;
    private String createdAt;
}
