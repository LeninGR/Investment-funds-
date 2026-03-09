package com.investment.funds.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.investment.funds.application.usecase.dto.GetTransactionHistoryInput;
import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.model.enums.TransactionType;
import com.investment.funds.domain.service.TransactionService;

@ExtendWith(MockitoExtension.class)
class GetTransactionHistoryUseCaseTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private GetTransactionHistoryUseCase getTransactionHistory;

    @Test
    void execute_ShouldReturnTransactionHistory_WhenFound() {
        // Arrange
        String clientId = "client1";
        Transaction t1 = new Transaction(clientId, "fund1", TransactionType.OPENING, new BigDecimal("1000"));
        Transaction t2 = new Transaction(clientId, "fund2", TransactionType.CANCELLATION, new BigDecimal("2000"));

        when(transactionService.getHistory(clientId)).thenReturn(List.of(t1, t2));

        // Act
        List<Transaction> history = getTransactionHistory.execute(new GetTransactionHistoryInput(clientId));

        // Assert
        assertEquals(2, history.size());
        assertEquals(t1, history.get(0));
        assertEquals(t2, history.get(1));
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenNoTransactions() {
        // Arrange
        String clientId = "client1";
        when(transactionService.getHistory(clientId)).thenReturn(List.of());

        // Act
        List<Transaction> history = getTransactionHistory.execute(new GetTransactionHistoryInput(clientId));

        // Assert
        assertEquals(0, history.size());
    }
}
