package com.investment.funds.infrastructure.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.investment.funds.application.usecase.UseCase;
import com.investment.funds.application.usecase.dto.GetTransactionHistoryInput;
import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.model.enums.TransactionType;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UseCase<GetTransactionHistoryInput, List<Transaction>> getTransactionHistoryUseCase;

    @Test
    void getHistory_ShouldReturnTransactions_WhenFound() throws Exception {
        // Arrange
        String clientId = "client1";
        List<Transaction> transactions = List.of(
                new Transaction("t1", clientId, "fund1", TransactionType.OPENING, new BigDecimal("100"),
                        LocalDateTime.now()),
                new Transaction("t2", clientId, "fund2", TransactionType.CANCELLATION, new BigDecimal("200"),
                        LocalDateTime.now()));

        when(getTransactionHistoryUseCase.execute(new GetTransactionHistoryInput(clientId))).thenReturn(transactions);

        // Act & Assert
        mockMvc.perform(get("/transactions/history/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("t1"))
                .andExpect(jsonPath("$[0].amount").value(100))
                .andExpect(jsonPath("$[1].id").value("t2"))
                .andExpect(jsonPath("$[1].amount").value(200));
    }

    @Test
    void shouldReturnEmptyListWhenNoTransactions() throws Exception {
        // Arrange
        String clientId = "client1";
        when(getTransactionHistoryUseCase.execute(new GetTransactionHistoryInput(clientId))).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/transactions/history/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
