package com.dev.quikkkk.repository;

import com.dev.quikkkk.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByTaskId(String taskId);
}
