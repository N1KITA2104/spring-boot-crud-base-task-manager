package com.example.springbootcrudbasetaskmanager.mapper;

import com.example.springbootcrudbasetaskmanager.dto.TaskRequest;
import com.example.springbootcrudbasetaskmanager.dto.TaskResponse;
import com.example.springbootcrudbasetaskmanager.entity.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }

    public static Task toEntity(TaskRequest request) {
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .build();
    }

    public static void updateEntity(Task task, TaskRequest request) {
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
    }
}
