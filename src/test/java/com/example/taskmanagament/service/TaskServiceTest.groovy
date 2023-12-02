package com.example.taskmanagament.service

import com.example.taskmanagament.dao.entity.Task
import com.example.taskmanagament.repository.TaskRepository
import com.example.taskmanagament.service.impl.TaskServiceImpl
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime


class TaskServiceTest extends Specification {

    @Subject
    TaskServiceImpl taskService

    def setup() {
        taskService = new TaskServiceImpl(Mock(TaskRepository))
    }

    def "createTask should create a new task"() {
        given:
        Task task = new Task(description: "Test Task", priority: "High", deadline: LocalDate.now().plusDays(7))

        when:
        Task createdTask = taskService.createTask(task)

        then:
        createdTask.id != null
        createdTask.description == task.description
        createdTask.priority == task.priority
        createdTask.deadline == task.deadline
        !createdTask.completed
    }

    def "getAllTasks should return a list of tasks"() {
        given:
        Task task1 = new Task(description: "Task 1", priority: "Low", deadline: LocalDateTime.now().plusDays(3))
        Task task2 = new Task(description: "Task 2", priority: "Medium", deadline: LocalDateTime.now().plusDays(5))

        and:
        taskService.createTask(task1)
        taskService.createTask(task2)

        when:
        List<Task> tasks = taskService.getAllTasks()

        then:
        tasks.size() == 2
        tasks*.description == ["Task 1", "Task 2"]
    }
}
