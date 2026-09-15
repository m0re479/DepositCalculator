package ru.neoflex.depositcalculator.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculationRequestDTOTest {
    private static Validator validator;

    @BeforeAll
    static void setUpValidator(){
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Пустое значение 'Сумма'")
    void testWhenAmountIsEmpty() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                null,
                12,
                new BigDecimal("8.5")
        );

        Set<ConstraintViolation<CalculationRequestDTO>> violations = validator.validate(requestDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Заполните поле 'Сумма'");
    }

    @Test
    @DisplayName("Нарушение нижней границы значения 'Сумма'")
    void testWhenAmountIsLessThan1000() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("999"),
                12,
                new BigDecimal("8.5")
        );

        Set<ConstraintViolation<CalculationRequestDTO>> violations = validator.validate(requestDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Минимальная сумма: 1000");
    }

    @Test
    @DisplayName("Нарушение верхней границы значения 'Сумма'")
    void testWhenAmountIsMoreThan10000000() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000001"),
                12,
                new BigDecimal("8.5")
        );

        Set<ConstraintViolation<CalculationRequestDTO>> violations = validator.validate(requestDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Максимальная сумма: 10 000 000");
    }

    @Test
    @DisplayName("Пустое значение 'Срок'")
    void testWhenMonthsIsEmpty() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                null,
                new BigDecimal("8.5")
        );

        Set<ConstraintViolation<CalculationRequestDTO>> violations = validator.validate(requestDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Заполните поле 'Срок'");
    }

    @Test
    @DisplayName("Нарушение нижней границы значения 'Срок'")
    void testWhenMonthsIsLessThan1() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                0,
                new BigDecimal("8.5")
        );

        Set<ConstraintViolation<CalculationRequestDTO>> violations = validator.validate(requestDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Минимальный срок: 1 месяц");
    }

    @Test
    @DisplayName("Нарушение верхней границы значения 'Срок'")
    void testWhenMonthsIsMoreThan60() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                61,
                new BigDecimal("8.5")
        );

        Set<ConstraintViolation<CalculationRequestDTO>> violations = validator.validate(requestDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Максимальный срок: 60 месяцев");
    }

    @Test
    @DisplayName("Пустое значение 'Годовая ставка'")
    void testWhenRateIsEmpty() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                5,
                null
        );

        Set<ConstraintViolation<CalculationRequestDTO>> violations = validator.validate(requestDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Заполните поле 'Годовая ставка'");
    }

    @Test
    @DisplayName("Нарушение нижней границы значения 'Годовая ставка'")
    void testWhenRateIsLessThan1() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                5,
                new BigDecimal("0.9")
        );

        Set<ConstraintViolation<CalculationRequestDTO>> violations = validator.validate(requestDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Минимальная ставка: 1%");
    }

    @Test
    @DisplayName("Нарушение верхней границы значения 'Годовая ставка'")
    void testWhenRateIsMoreThan20() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                5,
                new BigDecimal("20.1")
        );

        Set<ConstraintViolation<CalculationRequestDTO>> violations = validator.validate(requestDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Максимальная ставка: 20%");
    }
}
