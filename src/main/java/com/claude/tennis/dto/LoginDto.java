package com.claude.tennis.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "Login is mandatory") String login,
        @NotBlank(message = "Password is mandatory") String password) {
}