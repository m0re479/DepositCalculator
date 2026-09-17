export interface CalculationRequest {
    amount: number;
    months: number;
    rate: number;
}

export interface CalculationResponse {
    total: number;
    profit: number;
}
