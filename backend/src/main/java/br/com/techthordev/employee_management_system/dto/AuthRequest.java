package br.com.techthordev.employee_management_system.dto;

public record AuthRequest(
        String username,
        String password
) {}
