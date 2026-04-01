package com.example.springbootcrudbasetaskmanager.service;

import com.example.springbootcrudbasetaskmanager.dto.TaskRequest;
import com.example.springbootcrudbasetaskmanager.dto.TaskResponse;
import com.example.springbootcrudbasetaskmanager.entity.Task;
import com.example.springbootcrudbasetaskmanager.entity.TaskStatus;
import com.example.springbootcrudbasetaskmanager.mapper.TaskMapper;
import com.example.springbootcrudbasetaskmanager.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskResponse> getAllTasks(TaskStatus status, LocalDate createdAt, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Task> tasks;

        if (status != null && createdAt != null) {
            tasks = taskRepository.findByStatusAndCreatedAt(status, createdAt, pageable);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status, pageable);
        } else if (createdAt != null) {
            tasks = taskRepository.findByCreatedAt(createdAt, pageable);
        } else {
            tasks = taskRepository.findAll(pageable);
        }

        return tasks.map(TaskMapper::toResponse);
    }

    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        return TaskMapper.toResponse(task);
    }

    public TaskResponse createTask(TaskRequest request) {

        Task task = TaskMapper.toEntity(request);
        Task saved = taskRepository.save(task);
        return TaskMapper.toResponse(saved);
    }

    public TaskResponse updateTask(Long id, TaskRequest updatedTask) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        TaskMapper.updateEntity(task, updatedTask);
        Task saved = taskRepository.save(task);

        return TaskMapper.toResponse(saved);
    }

    public void deleteTask(Long id) {

        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found");
        }

        taskRepository.deleteById(id);
    }
}
