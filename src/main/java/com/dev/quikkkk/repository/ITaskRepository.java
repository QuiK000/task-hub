package com.dev.quikkkk.repository;

import com.dev.quikkkk.entity.Project;
import com.dev.quikkkk.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, String> {
    List<Task> findAllByProjectId(Project projectId);
}
