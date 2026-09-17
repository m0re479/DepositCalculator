import type { ApiErrorResponse } from "../types/api";
import type { CalculationRequest, CalculationResponse } from "../types/calculator";

const API_URL = 'http://localhost:8080/api/calculate';

export class ValidationError extends Error {
    fieldErrors: Record<string, string>;

    constructor(fieldErrors: Record<string, string>, message = 'Ошибка валидации полей') {
        super(message);
        this.name = 'ValidationError';
        this.fieldErrors = fieldErrors;
    }
}

export async function calculateDeposit(data: CalculationRequest): Promise<CalculationResponse> 
{
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        const errorData: ApiErrorResponse = await response.json();

        // Если получили ошибки конкретных полей с бэкенда
        if (errorData.errors && Object.keys(errorData.errors).length > 0) {
            throw new ValidationError(errorData.errors);
        }
        // Иначе
        throw new Error(errorData.errorMessage || 'Произошла ошибка при расчёте');
        
    }

    const result: CalculationResponse = await response.json();

    return result;
}
