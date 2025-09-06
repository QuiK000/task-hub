package com.dev.quikkkk.repository;

import com.dev.quikkkk.entity.redis.TaskRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRedisRepository extends CrudRepository<TaskRedisEntity, String> {
}
