package ru.neoflex.depositcalculator.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.depositcalculator.dto.CalculationRequestDTO;
import ru.neoflex.depositcalculator.dto.CalculationResponseDTO;
import ru.neoflex.depositcalculator.services.CalculationService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class CalculationController {
    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping("/calculate")
    public CalculationResponseDTO depositCalculation(
            @RequestBody @Valid CalculationRequestDTO requestDTO
    ) {
        return calculationService.calculate(requestDTO);
    }
}
