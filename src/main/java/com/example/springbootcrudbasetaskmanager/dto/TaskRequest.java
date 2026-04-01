package com.example.springbootcrudbasetaskmanager.dto;

import com.example.springbootcrudbasetaskmanager.entity.TaskStatus;
import jakarta.validation.constraints.*;

public record TaskRequest(

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 100, message = "Title must be 3-100 characters")
        String title,

        @Size(max = 500, message = "Description must be <= 500 characters")
        String description,

        @NotNull(message = "Status is required")
        TaskStatus status
) {}