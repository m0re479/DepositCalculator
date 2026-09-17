import { useState } from "react";
import { calculateDeposit, ValidationError } from './api/calculatorApi';
import './App.css'
import { CalculatorForm } from './components/CalculatorForm';
import { ResultCard } from './components/ResultCard';
import type { CalculationRequest, CalculationResponse } from './types/calculator';

function App() {
  const [initialAmount, setInitialAmount] = useState<number>(0);
  const [result, setResult] = useState<CalculationResponse | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [serverErrors, setServerErrors] = useState<Record<string, string>>({});
  const [generalError, setGeneralError] = useState<string | null>(null);

  const handleCalculate = async (data: CalculationRequest) => {
    setIsLoading(true);
    setServerErrors({});
    setGeneralError(null);
    setResult(null);

    try {
      const response = await calculateDeposit(data);
      setResult(response);
      setInitialAmount(data.amount);
    } catch (err) {
      if (err instanceof ValidationError) {
        setServerErrors(err.fieldErrors);
      } else {
        const errorMsg = (err as Error).message;

        if (errorMsg.includes('Failed to fetch')) {
          setGeneralError('Не удалось связаться с сервером. \nПовторите попытку позже.')
        } else {
          setGeneralError(`Ошибка: ${errorMsg}`);
        }
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <div className='app-container'>
        <div className='calculator-card'>
          <h1 className='title'>Калькулятор вклада</h1>

          {generalError && (
            <div className='general-error'>
              {generalError}
            </div>
          )}

          <CalculatorForm
            onSubmit={handleCalculate}
            isLoading={isLoading}
            serverErrors={serverErrors}
          />

          {result && (
            <ResultCard
              initialAmount={initialAmount}
              result={result}
            />
          )}
        </div>
      </div >
    </>
  )
}

export default App
