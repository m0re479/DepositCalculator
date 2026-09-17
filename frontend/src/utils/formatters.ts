const rubleFormatter = new Intl.NumberFormat('ru-RU', {
    style: 'currency',
    currency: 'RUB',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
});

export function formatCurrency(value: number): string {
    return rubleFormatter.format(value);
}