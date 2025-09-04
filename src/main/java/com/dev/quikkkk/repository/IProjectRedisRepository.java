package com.dev.quikkkk.repository;

import com.dev.quikkkk.entity.redis.ProjectRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRedisRepository extends CrudRepository<ProjectRedisEntity, String> {
}
