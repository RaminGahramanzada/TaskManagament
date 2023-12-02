package com.example.taskmanagament.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder

@AllArgsConstructor
@RequiredArgsConstructor
public class TaskDto {

    private Long id;
    private String name;
    private String description;
    private int priority;
    private LocalDateTime deadline;
}
