package com.dev.quikkkk.entity.redis;

import com.dev.quikkkk.entity.common.TaskStatus;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TaskRedisEntity {
    @Id
    private String id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String projectId;
    private String assigneeId;
    private String createdDate;

    @TimeToLive
    private long timeToLive;
}
