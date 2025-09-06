package com.dev.quikkkk.entity.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;

@RedisHash("projects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRedisEntity {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private LocalDateTime createdDate;
    @TimeToLive
    private long timeToLive;
}
