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

@RedisHash("users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRedisEntity {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean enabled;
    private boolean locked;
    private boolean expired;
    private boolean emailVerified;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @TimeToLive
    private long timeToLive;
}
