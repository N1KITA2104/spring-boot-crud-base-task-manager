package com.example.springbootcrudbasetaskmanager.service;

import com.example.springbootcrudbasetaskmanager.entity.Task;
import com.example.springbootcrudbasetaskmanager.entity.TaskStatus;
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

    public Page<Task> getAllTasks(TaskStatus status, LocalDate createdAt, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (status != null && createdAt != null) {
            return taskRepository.findByStatusAndCreatedAt(status, createdAt, pageable);
        }

        if (status != null) {
            return taskRepository.findByStatus(status, pageable);
        }

        if (createdAt != null) {
            return taskRepository.findByCreatedAt(createdAt, pageable);
        }

        return taskRepository.findAll(pageable);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    public void createTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        taskRepository.delete(task);
    }

    public Task updateTask(Long id, Task updated_task) {
        Task task = taskRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setTitle(updated_task.getTitle());
        task.setDescription(updated_task.getDescription());
        task.setStatus(updated_task.getStatus());

        return taskRepository.save(task);
    }
}
