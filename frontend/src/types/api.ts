export interface ApiErrorResponse {
    timestamp: string;
    status: number;
    errorMessage: string;
    errors?: Record<string, string>;
}
