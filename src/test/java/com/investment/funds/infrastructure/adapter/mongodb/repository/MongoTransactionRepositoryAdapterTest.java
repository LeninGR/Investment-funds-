package com.investment.funds.infrastructure.adapter.mongodb.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.model.enums.TransactionType;
import com.investment.funds.infrastructure.adapter.mongodb.document.TransactionDocument;

@ExtendWith(MockitoExtension.class)
class MongoTransactionRepositoryAdapterTest {

    @Mock
    private SpringDataTransactionRepository springDataTransactionRepository;

    @InjectMocks
    private MongoTransactionRepositoryAdapter mongoTransactionRepositoryAdapter;

    @Test
    void shouldSave() {
        // Arrange
        Transaction transaction = new Transaction("1", "client1", "fund1", TransactionType.OPENING, BigDecimal.TEN, LocalDateTime.now());

        // Act
        mongoTransactionRepositoryAdapter.save(transaction);

        // Assert
        ArgumentCaptor<TransactionDocument> captor = ArgumentCaptor.forClass(TransactionDocument.class);
        verify(springDataTransactionRepository).save(captor.capture());
        TransactionDocument savedDocument = captor.getValue();
        assertEquals("1", savedDocument.getId());
        assertEquals("client1", savedDocument.getClientId());
        assertEquals("fund1", savedDocument.getFundId());
        assertEquals("OPENING", savedDocument.getType());
        assertEquals(BigDecimal.TEN, savedDocument.getAmount());
    }

    @Test
    void shouldFindByClientId() {
        // Arrange
        String clientId = "client1";
        LocalDateTime now = LocalDateTime.now();
        TransactionDocument document = new TransactionDocument("1", clientId, "fund1", "OPENING", BigDecimal.TEN, now);
        when(springDataTransactionRepository.findByClientId(clientId)).thenReturn(List.of(document));

        // Act
        List<Transaction> result = mongoTransactionRepositoryAdapter.findByClientId(clientId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).id());
        assertEquals(clientId, result.get(0).clientId());
        assertEquals("fund1", result.get(0).fundId());
        assertEquals(TransactionType.OPENING, result.get(0).type());
        assertEquals(BigDecimal.TEN, result.get(0).amount());
        assertEquals(now, result.get(0).timestamp());
    }
}
