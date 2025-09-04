package com.dev.quikkkk.repository;

import com.dev.quikkkk.entity.redis.UserRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRedisRepository extends CrudRepository<UserRedisEntity, String> {
}
