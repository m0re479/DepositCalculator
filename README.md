# DepositCalculator
Fullstack-приложение для расчёта доходности банковских депозитов.

---

## 🛠 Стек технологий

* **Backend:** Java 17, Spring Boot 4.1.1, Spring Web, Jakarta Bean Validation, Swagger/OpenAPI 3.0.3, JUnit 5, AssertJ.
* **Frontend:** React, TypeScript, Vite, CSS Grid/Flexbox, Vitest.
* **Архитектура:** REST API, Clean Architecture, Stateless Service.

---

## 🚀 Быстрый запуск

### 1. Запуск Бэкенда (Spring Boot)
Требуется: **Java 17+** и **Maven**.

```bash
cd backend
# Windows:
./mvnw.cmd spring-boot:run

# Linux / macOS:
./mvnw spring-boot:run
```
> Бэкенд запустится на: `http://localhost:8080`

### 2. Запуск Фронтенда (React + Vite)
Требуется: **Node.js (v18+)** и **npm**.

```bash
cd frontend
npm install
npm run dev
```
> Фронтенд будет доступен по адресу: `http://localhost:5173`

## 📖 API Документация (Swagger)

Интерактивная документация и песочница для тестирования эндпоинтов доступны при запущенном бэкенде:
**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### Спецификация эндпоинта:
* **URL:** `POST /api/calculate`
* **Headers:** `Content-Type: application/json`

**Пример запроса:**
```json
{
  "amount": 100000,
  "months": 12,
  "rate": 8.5
}
```

**Пример ответа (200 OK):**
```json
{
  "total": 108839.09,
  "profit": 8839.09
}
```

---

## 🧪 Запуск тестов

### Тесты Бэкенда (Unit + Integration):
```bash
cd backend
./mvnw test
```

### Тесты Фронтенда (Vitest):
```bash
cd frontend
npm run test
```

---

## 💡 Особенности реализации

* Расчет формулы выполняется строго на `BigDecimal` с `MathContext.DECIMAL64`. Округление (`HALF_UP`) до 2 знаков применяется только к финальному результату.
* Клиентская валидация в React (проверка чисел $> 0$) + строгая валидация на бэкенде через Jakarta Validation.
* Реализован `GlobalExceptionHandler` (`@RestControllerAdvice`), возвращающий структурированные ошибки с кодами `400`, `405`, `500`.

---

<details>
<summary>📋 Развернуть исходное Техническое задание</summary>

### 1. Endpoint
`POST /api/calculate`

### 2. Входные данные (JSON)
* `amount` — сумма (от 1 000 до 10 000 000)
* `months` — срок в месяцах (от 1 до 60)
* `rate` — годовая ставка (от 1% до 20%)

### 3. Логика расчета
* **Формула:**
  $$\text{Итог} = \text{Сумма} \times \left(1 + \frac{\text{Ставка}}{1200}\right)^{\text{Месяцы}}$$
* **Прибыль:**
  $$\text{Прибыль} = \text{Итог} - \text{Сумма}$$

### 4. Требования к реализации Backend-а
* Использовать Spring Boot.
* Добавить валидацию входных данных.
* Использовать BigDecimal для точности.

## Пример входных данных:
{
  "amount": 100000,
  "months": 12,
  "rate": 8.5
}

## Пример выходных данных:
{
  "total": 108300.50,
  "profit": 8300.50
}


### 5. Требования к UI:
* Использовать React + TypeScript.
* Форма с 3 полями ввода и кнопкой расчета.
* При нажатии кнопки отправлять POST-запрос на /api/calculate.
* Вывод начальной суммы, итога и прибыли под формой.
* Клиентская валидация (числа > 0).

## Пример интерфейса:
```text
[ Сумма вклада:     100 000 ₽     ]
[ Срок (месяцы):    12            ]
[ Годовая ставка:   8.5 %         ]
         [ Рассчитать ]
                 
Начальная сумма: 100 000 ₽
Итоговая сумма:  108 300.50 ₽
Доход:           8 300.50 ₽
```
</details>
