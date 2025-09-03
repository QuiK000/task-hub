package com.dev.quikkkk.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
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
    private String createdDate;
    private String lastModifiedDate;

    @TimeToLive
    private long timeToLive;
}
