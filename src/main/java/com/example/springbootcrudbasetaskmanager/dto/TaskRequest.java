package com.example.springbootcrudbasetaskmanager.dto;

import com.example.springbootcrudbasetaskmanager.entity.TaskStatus;

public record TaskRequest(
        String title,
        String description,
        TaskStatus status
) {}
