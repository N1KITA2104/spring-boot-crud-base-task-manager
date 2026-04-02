package com.example.springbootcrudbasetaskmanager.service;

import com.example.springbootcrudbasetaskmanager.dto.TaskRequest;
import com.example.springbootcrudbasetaskmanager.dto.TaskResponse;
import com.example.springbootcrudbasetaskmanager.entity.Task;
import com.example.springbootcrudbasetaskmanager.entity.TaskStatus;
import com.example.springbootcrudbasetaskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask() {
        TaskRequest request = new TaskRequest("Test", "desc", TaskStatus.TODO);

        Task saved = new Task(1L, "Test", "desc", TaskStatus.TODO, LocalDate.now());

        when(taskRepository.save(any(Task.class)))
                .thenReturn(saved);

        TaskResponse result = taskService.createTask(request);

        assertEquals("Test", result.title());
        assertNotNull(result);
    }

    @Test
    void getAllTasks() {
        Task task1 = new Task(1L, "Test1", "desc", TaskStatus.TODO, LocalDate.now());
        Task task2 = new Task(2L, "Test2", "desc", TaskStatus.DONE, LocalDate.now());

        List<Task> taskList = List.of(task1, task2);
        Page<Task> page = new PageImpl<>(taskList);

        when(taskRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        Page<TaskResponse> result = taskService.getAllTasks(null, null, 0, 10);

        assertEquals(2, result.getTotalElements());
        assertEquals("Test1", result.getContent().get(0).title());
    }

    @Test
    void getTaskById() {
        Task task = new Task(1L, "Test", "desc", TaskStatus.TODO, LocalDate.now());

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        TaskResponse result = taskService.getTaskById(1L);

        assertEquals("Test", result.title());
    }

    @Test
    void updateTask() {
        Task task = new Task(1L, "Test", "desc", TaskStatus.TODO, LocalDate.now());
        TaskRequest updatedTask = new TaskRequest("New test", "desc1", TaskStatus.DONE);

        Task saved = new Task(1L, "New test", "desc1", TaskStatus.DONE, LocalDate.now());

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(any(Task.class)))
                .thenReturn(saved);

        TaskResponse result = taskService.updateTask(1L, updatedTask);

        assertEquals(TaskStatus.DONE, result.status());
    }

    @Test
    void deleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository).existsById(1L);
        verify(taskRepository).deleteById(1L);
    }
}