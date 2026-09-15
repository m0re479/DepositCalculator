package ru.neoflex.DepositCalculator.dto;

import java.math.BigDecimal;

public record CalculationResponseDTO(
        BigDecimal total,
        BigDecimal profit
) { }
