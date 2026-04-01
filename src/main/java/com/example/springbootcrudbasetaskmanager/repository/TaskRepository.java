package com.example.springbootcrudbasetaskmanager.repository;

import com.example.springbootcrudbasetaskmanager.entity.Task;
import com.example.springbootcrudbasetaskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    Page<Task> findByCreatedAt(LocalDate createdAt, Pageable pageable);

    Page<Task> findByStatusAndCreatedAt(TaskStatus status, LocalDate createdAt, Pageable pageable);
}
