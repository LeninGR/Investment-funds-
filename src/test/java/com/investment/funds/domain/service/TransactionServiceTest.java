package com.investment.funds.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.model.enums.TransactionType;
import com.investment.funds.domain.port.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void save_ShouldCallRepositorySave() {
        // Arrange
        Transaction transaction = new Transaction("client1", "fund1", TransactionType.OPENING, new BigDecimal("100"));

        // Act
        transactionService.save(transaction);

        // Assert
        verify(transactionRepository).save(transaction);
    }

    @Test
    void getHistory_ShouldReturnTransactions_WhenFound() {
        // Arrange
        String clientId = "client1";
        List<Transaction> transactions = List.of(
                new Transaction(clientId, "fund1", TransactionType.OPENING, new BigDecimal("100")),
                new Transaction(clientId, "fund2", TransactionType.CANCELLATION, new BigDecimal("200")));

        when(transactionRepository.findByClientId(clientId)).thenReturn(transactions);

        // Act
        List<Transaction> result = transactionService.getHistory(clientId);

        // Assert
        assertEquals(transactions, result);
    }

    @Test
    void hasActiveSubscription_ShouldReturnTrue_WhenLastTransactionIsOpening() {
        // Arrange
        String clientId = "client1";
        String fundId = "fund1";

        // Transaction 1: Opening (Oldest)
        Transaction t1 = new Transaction(UUID.randomUUID().toString(), clientId, fundId, TransactionType.OPENING,
                new BigDecimal("100"), LocalDateTime.now().minusDays(2));
        // Transaction 2: Cancellation (Middle)
        Transaction t2 = new Transaction(UUID.randomUUID().toString(), clientId, fundId, TransactionType.CANCELLATION,
                new BigDecimal("100"), LocalDateTime.now().minusDays(1));
        // Transaction 3: Opening (Newest - Active)
        Transaction t3 = new Transaction(UUID.randomUUID().toString(), clientId, fundId, TransactionType.OPENING,
                new BigDecimal("100"), LocalDateTime.now());

        when(transactionRepository.findByClientId(clientId)).thenReturn(List.of(t1, t2, t3));

        // Act
        boolean result = transactionService.hasActiveSubscription(clientId, fundId);

        // Assert
        assertTrue(result);
    }

    @Test
    void hasActiveSubscription_ShouldReturnFalse_WhenLastTransactionIsCancellation() {
        // Arrange
        String clientId = "client1";
        String fundId = "fund1";

        // Transaction 1: Opening (Oldest)
        Transaction t1 = new Transaction(UUID.randomUUID().toString(), clientId, fundId, TransactionType.OPENING,
                new BigDecimal("100"), LocalDateTime.now().minusDays(1));
        // Transaction 2: Cancellation (Newest - Inactive)
        Transaction t2 = new Transaction(UUID.randomUUID().toString(), clientId, fundId, TransactionType.CANCELLATION,
                new BigDecimal("100"), LocalDateTime.now());

        when(transactionRepository.findByClientId(clientId)).thenReturn(List.of(t1, t2));

        // Act
        boolean result = transactionService.hasActiveSubscription(clientId, fundId);

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldNotHaveActiveSubscriptionWhenNoTransactionsForFund() {
        // Arrange
        String clientId = "client1";
        String fundId = "fund1";

        // Transactions for OTHER funds
        Transaction t1 = new Transaction(clientId, "otherFund", TransactionType.OPENING, new BigDecimal("100"));

        when(transactionRepository.findByClientId(clientId)).thenReturn(List.of(t1));

        // Act
        boolean result = transactionService.hasActiveSubscription(clientId, fundId);

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldNotHaveActiveSubscriptionWhenNoTransactionsAtAll() {
        // Arrange
        String clientId = "client1";
        String fundId = "fund1";

        when(transactionRepository.findByClientId(clientId)).thenReturn(List.of());

        // Act
        boolean result = transactionService.hasActiveSubscription(clientId, fundId);

        // Assert
        assertFalse(result);
    }
}
