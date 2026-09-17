package ru.neoflex.depositcalculator.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.neoflex.depositcalculator.dto.CalculationRequestDTO;
import ru.neoflex.depositcalculator.dto.CalculationResponseDTO;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CalculationServiceTest {
    private CalculationService calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService();
    }

    @Test
    @DisplayName("Пример из ТЗ")
    void testCalculateForExampleData() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("100000"),
                12,
                new BigDecimal("8.5")
        );

        CalculationResponseDTO responseDTO = calculationService.calculate(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.total()).isEqualTo(new BigDecimal("108839.09"));
        assertThat(responseDTO.profit()).isEqualTo(new BigDecimal("8839.09"));
    }

    @Test
    @DisplayName("Нижняя граница значения 'сумма'")
    void testCalculateForTheLowerLimitOfTheAmount() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("1000"),
                10,
                new BigDecimal("5.4")
        );

        CalculationResponseDTO responseDTO = calculationService.calculate(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.total()).isEqualTo(new BigDecimal("1045.92"));
        assertThat(responseDTO.profit()).isEqualTo(new BigDecimal("45.92"));
    }

    @Test
    @DisplayName("Верхняя граница значения 'сумма'")
    void testCalculateForTheUpperLimitOfTheAmount() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000000"),
                40,
                new BigDecimal("15.7")
        );

        CalculationResponseDTO responseDTO = calculationService.calculate(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.total()).isEqualTo(new BigDecimal("16819257.75"));
        assertThat(responseDTO.profit()).isEqualTo(new BigDecimal("6819257.75"));
    }

    @Test
    @DisplayName("Нижняя граница значения 'срок'")
    void testCalculateForTheLowerLimitOfTheMonths() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                1,
                new BigDecimal("12.1")
        );

        CalculationResponseDTO responseDTO = calculationService.calculate(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.total()).isEqualTo(new BigDecimal("10100.83"));
        assertThat(responseDTO.profit()).isEqualTo(new BigDecimal("100.83"));
    }

    @Test
    @DisplayName("Верхняя граница значения 'срок'")
    void testCalculateForTheUpperLimitOfTheMonths() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                60,
                new BigDecimal("12.1")
        );

        CalculationResponseDTO responseDTO = calculationService.calculate(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.total()).isEqualTo(new BigDecimal("18257.12"));
        assertThat(responseDTO.profit()).isEqualTo(new BigDecimal("8257.12"));
    }


    @Test
    @DisplayName("Нижняя граница значения 'годовая ставка'")
    void testCalculateForTheLowerLimitOfTheAnnualRate() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                27,
                new BigDecimal("1.0")
        );

        CalculationResponseDTO responseDTO = calculationService.calculate(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.total()).isEqualTo(new BigDecimal("10227.45"));
        assertThat(responseDTO.profit()).isEqualTo(new BigDecimal("227.45"));
    }

    @Test
    @DisplayName("Верхняя граница значения 'годовая ставка'")
    void testCalculateForTheUpperLimitOfTheAnnualRate() {
        CalculationRequestDTO requestDTO = new CalculationRequestDTO(
                new BigDecimal("10000"),
                27,
                new BigDecimal("20.0")
        );

        CalculationResponseDTO responseDTO = calculationService.calculate(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.total()).isEqualTo(new BigDecimal("15625.06"));
        assertThat(responseDTO.profit()).isEqualTo(new BigDecimal("5625.06"));
    }
}
