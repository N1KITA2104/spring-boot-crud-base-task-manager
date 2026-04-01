package com.example.springbootcrudbasetaskmanager.dto;

public record ErrorResponse(
        int status,
        String message
) {}
