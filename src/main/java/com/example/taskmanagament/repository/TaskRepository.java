package com.example.taskmanagament.repository;

import com.example.taskmanagament.dao.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByCategory(String category);
    List<Task> findByIsCompleted(boolean isCompleted);



}
