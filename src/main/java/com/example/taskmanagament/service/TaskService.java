package com.example.taskmanagament.service;

import com.example.taskmanagament.dao.entity.Task;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Component
public interface TaskService {

    Task createTask(Task task);

    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long id);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);

    Task markTaskAsCompleted(Long id);

    List<Task> getCompletedTasks();

    List<Task> getTasksByCategory(String category);
}
