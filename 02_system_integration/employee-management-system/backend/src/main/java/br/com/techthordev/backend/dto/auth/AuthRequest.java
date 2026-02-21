package br.com.techthordev.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(

    @NotBlank String username,
    @NotBlank String password
    
) {}
