package ru.neoflex.depositcalculator.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String errorMessage,
        Map<String, String> errors
) { }
