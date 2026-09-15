package ru.neoflex.depositcalculator.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.math.BigDecimal;

public record CalculationRequestDTO(
        @NotNull(message = "Заполните поле 'Сумма'")
        @DecimalMin(value = "1000", message = "Минимальная сумма: 1000")
        @DecimalMax(value = "10_000_000", message = "Максимальная сумма: 10 000 000")
        BigDecimal amount,

        @NotNull(message = "Заполните поле 'Срок'")
        @Min(value = 1, message = "Минимальный срок: 1 месяц")
        @Max(value = 60, message = "Максимальный срок: 60 месяцев")
        int months,

        @NotNull(message = "Заполните поле 'Годовая ставка'")
        @DecimalMin(value = "1.0", message = "Минимальная ставка: 1%")
        @DecimalMax(value = "20.0", message = "Максимальная ставка: 20%")
        BigDecimal rate
) { }
