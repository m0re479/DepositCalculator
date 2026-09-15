package ru.neoflex.depositcalculator.dto;

import java.math.BigDecimal;

public record CalculationResponseDTO(
        BigDecimal total,
        BigDecimal profit
) { }
