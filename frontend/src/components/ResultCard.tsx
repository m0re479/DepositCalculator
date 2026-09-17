import type { CalculationResponse } from "../types/calculator";
import { formatCurrency } from "../utils/formatters";

interface ResultCardProps {
    initialAmount: number;
    result: CalculationResponse;
}

export function ResultCard({ initialAmount, result }: ResultCardProps) {
    return (
        <div className="result-card">
            <h2>Результаты расчёта</h2>
            <div className="result-item">
                <span>Начальная сумма: </span>
                <strong>{formatCurrency(initialAmount)}</strong>
            </div>
            <div className="result-item">
                <span>Итоговая сумма: </span>
                <strong>{formatCurrency(result.total)}</strong>
            </div>
            <div className="result-item">
                <span>Доход: </span>
                <strong>{formatCurrency(result.profit)}</strong>
            </div>
        </div>
    )
}
