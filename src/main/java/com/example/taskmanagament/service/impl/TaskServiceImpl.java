package com.example.taskmanagament.service.impl;

import com.example.taskmanagament.dao.entity.Task;
import com.example.taskmanagament.repository.TaskRepository;
import com.example.taskmanagament.service.TaskService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isPresent()) {
            Task taskToUpdate = existingTask.get();
            taskToUpdate.setName(updatedTask.getName());
            taskToUpdate.setPriority(updatedTask.getPriority());
            taskToUpdate.setDeadline(updatedTask.getDeadline());
            taskToUpdate.setCategory(updatedTask.getCategory());
            taskToUpdate.setTaskStatus(updatedTask.getTaskStatus());
            return taskRepository.save(taskToUpdate);
        } else {

            return null;
        }
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task markTaskAsCompleted(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCompleted(true);
            return taskRepository.save(task);
        } else {

            return null;
        }
    }

    @Override
    public List<Task> getCompletedTasks() {
        return taskRepository.findByIsCompleted(true);
    }

    @Override
    public List<Task> getTasksByCategory(String category) {
        return taskRepository.findByCategory(category);
    }
}