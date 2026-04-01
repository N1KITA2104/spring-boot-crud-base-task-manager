package com.example.springbootcrudbasetaskmanager.dto;

import com.example.springbootcrudbasetaskmanager.entity.TaskStatus;

import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDate createdAt
) {}
