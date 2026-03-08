package com.investment.funds.infrastructure.adapter.mongodb.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.investment.funds.domain.model.Fund;
import com.investment.funds.infrastructure.adapter.mongodb.document.FundDocument;

@ExtendWith(MockitoExtension.class)
class MongoFundRepositoryAdapterTest {

    @Mock
    private SpringDataFundRepository springDataFundRepository;

    @InjectMocks
    private MongoFundRepositoryAdapter mongoFundRepositoryAdapter;

    @Test
    void shouldFindById() {
        // Arrange
        String id = "1";
        FundDocument document = new FundDocument(id, "Fund Name", BigDecimal.TEN, "Category");
        when(springDataFundRepository.findById(id)).thenReturn(Optional.of(document));

        // Act
        Optional<Fund> result = mongoFundRepositoryAdapter.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().id());
        assertEquals("Fund Name", result.get().name());
        assertEquals(BigDecimal.TEN, result.get().minAmount());
        assertEquals("Category", result.get().category());
    }

    @Test
    void shouldSave() {
        // Arrange
        Fund fund = new Fund("1", "Fund Name", BigDecimal.TEN, "Category");

        // Act
        mongoFundRepositoryAdapter.save(fund);

        // Assert
        ArgumentCaptor<FundDocument> captor = ArgumentCaptor.forClass(FundDocument.class);
        verify(springDataFundRepository).save(captor.capture());
        FundDocument savedDocument = captor.getValue();
        assertEquals("1", savedDocument.getId());
        assertEquals("Fund Name", savedDocument.getName());
        assertEquals(BigDecimal.TEN, savedDocument.getMinAmount());
        assertEquals("Category", savedDocument.getCategory());
    }
}
