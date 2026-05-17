package com.claude.tennis.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record RankDto(
                @Positive(message = "Position must be a positive number") int position,
                @PositiveOrZero(message = "Points must be more than zero") int points) {
}
