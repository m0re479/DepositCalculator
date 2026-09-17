import { useState, type SyntheticEvent } from "react";
import type { CalculationRequest } from "../types/calculator";

interface CalculatorFormProps {
    onSubmit: (data: CalculationRequest) => void;
    isLoading: boolean;
    serverErrors: Record<string, string>;
}

export function CalculatorForm({ onSubmit, isLoading, serverErrors }: CalculatorFormProps) {
    const [amount, setAmount] = useState('100000');
    const [months, setMonths] = useState('12');
    const [rate, setRate] = useState('8.5');

    const [clientErrors, setClientErrors] = useState<Record<string, string>>({});

    const clientValidate = (): boolean => {
        const errors: Record<string, string> = {};

        const numAmount = Number(amount);
        const numMonths = Number(months);
        const numRate = Number(rate);

        if (!amount || numAmount <= 0) {
            errors.amount = 'Сумма должна быть больше 0';
        }
        if (!months || numMonths <= 0) {
            errors.months = 'Срок должен быть целым числом больше 0';
        }
        if (!rate || numRate <= 0) {
            errors.rate = 'Ставка должна быть больше 0%';
        }

        setClientErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleSubmit = (e: SyntheticEvent) => {
        e.preventDefault();

        if (clientValidate()) {
            onSubmit({
                amount: Number(amount),
                months: Number(months),
                rate: Number(rate),
            });
        }
    };

    const amountError = clientErrors.amount || serverErrors.amount;
    const monthsError = clientErrors.months || serverErrors.months;
    const rateError = clientErrors.rate || serverErrors.rate;

    return (
        <form className="calculator-form" onSubmit={handleSubmit} noValidate>
            {/* Сумма */}
            <div className="form-item">
                <label htmlFor="amount">Сумма вклада (₽):</label>
                <input
                    id="amount"
                    type="number"
                    step="any"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    placeholder="Например, 100000"
                    className={amountError ? 'input-error' : ''}
                />
                {amountError && <span className="error-message">{amountError}</span>}
            </div>

            {/* Срок */}
            <div className="form-item">
                <label htmlFor="months">Срок (месяцы):</label>
                <input
                    id="months"
                    type="number"
                    value={months}
                    onChange={(e) => setMonths(e.target.value)}
                    placeholder="Например, 12"
                    className={monthsError ? 'input-error' : ''}
                />
                {monthsError && <span className="error-message">{monthsError}</span>}
            </div>

            {/* Ставка */}
            <div className="form-item">
                <label htmlFor="rate">Годовая ставка (%):</label>
                <input
                    id="rate"
                    type="number"
                    step="0.1"
                    value={rate}
                    onChange={(e) => setRate(e.target.value)}
                    placeholder="Например, 8.5"
                    className={rateError ? 'input-error' : ''}
                />
                {rateError && <span className="error-message">{rateError}</span>}
            </div>

            <button type="submit" className="submit-btn" disabled={isLoading}>
                {isLoading ? 'Рассчитываем...' : 'Рассчитать'}
            </button>
        </form>
    )
}
