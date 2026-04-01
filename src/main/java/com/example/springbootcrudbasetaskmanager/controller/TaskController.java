package com.example.springbootcrudbasetaskmanager.controller;

import com.example.springbootcrudbasetaskmanager.dto.TaskRequest;
import com.example.springbootcrudbasetaskmanager.dto.TaskResponse;
import com.example.springbootcrudbasetaskmanager.entity.TaskStatus;
import com.example.springbootcrudbasetaskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Page<TaskResponse> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) LocalDate createdAt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return taskService.getAllTasks(status, createdAt, page, size);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {
        return taskService.updateTask(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
