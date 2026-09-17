import { vi, beforeEach, describe, it, expect } from 'vitest';
import { calculateDeposit, ValidationError } from './calculatorApi';
import type { CalculationRequest } from '../types/calculator';

describe('calculatorApi: calculateDeposit', () => {
    beforeEach(() => {
        vi.restoreAllMocks();
    });

    const validRequest: CalculationRequest = {
        amount: 100000,
        months: 12,
        rate: 8.5,
    }

    it('успешный результат при валидных данных (HTTP 200)', async () => {
        const mockResponse = {
            total: 108839.09,
            profit: 8839.09
        };
        globalThis.fetch = vi.fn().mockResolvedValue({
            ok: true,
            json: async () => mockResponse,
        });

        const result = await calculateDeposit(validRequest);

        expect(result).toEqual(mockResponse);
        expect(globalThis.fetch).toHaveBeenCalledTimes(1);
    });

    it('ValidationError при нарушении границ значения amount (HTTP 400)', async () => {
        const mockResponse = {
            timestamp: '2026-08-21T12:00:00',
            status: 400,
            errorMessage: 'Bad Request',
            errors: { amount: 'Заполните поле "Сумма"' },
        };
        globalThis.fetch = vi.fn().mockResolvedValue({
            ok: false,
            json: async () => mockResponse,
        });

        const invalidRequest: CalculationRequest = {
            amount: 999,
            months: 12,
            rate: 8.5,
        }

        await expect(calculateDeposit(invalidRequest)).rejects.toThrow(ValidationError);
    });

    it('Error при системной ошибке (HTTP 500)', async () => {
        const mockResponse = {
            timestamp: '2026-08-21T12:00:00',
            status: 500,
            errorMessage: 'Внутренняя ошибка сервера',
        };
        globalThis.fetch = vi.fn().mockResolvedValue({
            ok: false,
            json: async () => mockResponse,
        });

        await expect(calculateDeposit(validRequest)).rejects.toThrow('Внутренняя ошибка сервера');
    });

    it('дефолтный текст ошибки при пустом ответе сервера', async () => {
        globalThis.fetch = vi.fn().mockResolvedValue({
            ok: false,
            json: async () => ({}),
        });

        await expect(calculateDeposit(validRequest)).rejects.toThrow('Произошла ошибка при расчёте');
    });
});
