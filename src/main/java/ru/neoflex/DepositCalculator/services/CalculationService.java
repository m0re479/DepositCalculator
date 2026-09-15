package ru.neoflex.DepositCalculator.services;

import org.springframework.stereotype.Service;
import ru.neoflex.DepositCalculator.dto.CalculationRequestDTO;
import ru.neoflex.DepositCalculator.dto.CalculationResponseDTO;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class CalculationService {
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;
    private static final BigDecimal DIVISOR = BigDecimal.valueOf(1200);
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public CalculationResponseDTO calculate(CalculationRequestDTO requestDTO) {
        // amount * ((1 + (rate/100/12)) ^ months)
        BigDecimal total = requestDTO.amount().multiply (
                // 1 + (rate/100/12)
                BigDecimal.ONE.add(requestDTO.rate().divide(DIVISOR, MATH_CONTEXT))
                        // (1 + (rate/100/12)) ^ months
                        .pow(requestDTO.months())
        );

        BigDecimal roundedTotal = total.setScale(SCALE, ROUNDING_MODE);
        BigDecimal profit = roundedTotal.subtract(requestDTO.amount());

        return new CalculationResponseDTO(roundedTotal, profit);
    }
}
