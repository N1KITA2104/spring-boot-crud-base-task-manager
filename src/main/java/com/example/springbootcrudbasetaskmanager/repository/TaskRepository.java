package com.example.springbootcrudbasetaskmanager.repository;

import com.example.springbootcrudbasetaskmanager.entity.Task;
import com.example.springbootcrudbasetaskmanager.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByCreatedAt(LocalDate createdAt);
}
