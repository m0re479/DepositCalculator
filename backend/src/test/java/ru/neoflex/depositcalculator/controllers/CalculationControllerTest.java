package ru.neoflex.depositcalculator.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CalculationControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("POST /api/calculate 200 ОК")
    void testCalculationForExampleData() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 100000,
                    "months": 12,
                    "rate": 8.5
                }
                """;

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(108839.09))
                .andExpect(jsonPath("$.profit").value(8839.09));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: amount = null")
    void testCalculationWhenAmountIsEmpty() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "months": 12,
                    "rate": 8.5
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.amount").value("Заполните поле 'Сумма'"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: amount меньше минимума")
    void testCalculationWhenAmountIsLessThan1000() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 999,
                    "months": 12,
                    "rate": 8.5
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.amount").value("Минимальная сумма: 1000"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: amount больше максимума")
    void testCalculationWhenAmountIsMoreThan10000000() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 10000001,
                    "months": 12,
                    "rate": 8.5
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.amount").value("Максимальная сумма: 10 000 000"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: months = null")
    void testCalculationWhenMonthsIsEmpty() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 10000,
                    "rate": 8.5
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.months").value("Заполните поле 'Срок'"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: months меньше минимума")
    void testCalculationWhenMonthsIsLessThan1() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 10000,
                    "months": 0,
                    "rate": 8.5
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.months").value("Минимальный срок: 1 месяц"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: months больше максимума")
    void testCalculationWhenMonthsIsMoreThan60() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 10000,
                    "months": 61,
                    "rate": 8.5
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.months").value("Максимальный срок: 60 месяцев"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: rate = null")
    void testCalculationWhenRateIsEmpty() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 10000,
                    "months": 12
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.rate").value("Заполните поле 'Годовая ставка'"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: rate меньше минимума")
    void testCalculationWhenRateIsLessThan1() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 10000,
                    "months": 12,
                    "rate": 0.9
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.rate").value("Минимальная ставка: 1%"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: rate больше максимума")
    void testCalculationWhenRateIsMoreThan20() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 10000,
                    "months": 12,
                    "rate": 20.1
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.rate").value("Максимальная ставка: 20%"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: JSON пуст")
    void testCalculationWhenJsonIsEmpty() throws Exception {
        String url = "/api/calculate";
        String requestJson = "{}";

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.amount").value("Заполните поле 'Сумма'"))
                .andExpect(jsonPath("$.errors.months").value("Заполните поле 'Срок'"))
                .andExpect(jsonPath("$.errors.rate").value("Заполните поле 'Годовая ставка'"));
    }

    @Test
    @DisplayName("POST /api/calculate 400 Bad Request: неверный тип данных")
    void testCalculationWhenDataTypeIsIncorrect() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": "abcd",
                    "months": 12,
                    "rate": 8.5
                }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.error").value("Проверьте типы и формат переданных полей"));
    }

    @Test
    @DisplayName("GET /api/calculate 405 Method Not Allowed: неверный HTTP-метод")
    void testCalculationWhenHttpMethodIsIncorrect() throws Exception {
        String url = "/api/calculate";

        mockMvc.perform(get(url))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status").value(405))
                .andExpect(jsonPath("$.errors.error").value("Метод GET не поддерживается. Используйте метод POST"));
    }

    @Test
    @DisplayName("POST /api/calculate 500 Internal Server Error: отсутствие заголовка Content-Type")
    void testCalculationWhenContentTypeIsEmpty() throws Exception {
        String url = "/api/calculate";
        String requestJson = """
                {
                    "amount": 10000,
                    "months": 12,
                    "rate": 8.5
                }
                """;

        mockMvc.perform(post(url)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.errors.error").value("Произошла непредвиденная ошибка"));
    }
}
