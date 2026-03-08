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

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.enums.NotificationPreference;
import com.investment.funds.infrastructure.adapter.mongodb.document.ClientDocument;

@ExtendWith(MockitoExtension.class)
class MongoClientRepositoryAdapterTest {

    @Mock
    private SpringDataClientRepository springDataClientRepository;

    @InjectMocks
    private MongoClientRepositoryAdapter mongoClientRepositoryAdapter;

    @Test
    void shouldFindById() {
        // Arrange
        String id = "1";
        ClientDocument document = new ClientDocument(id, "Client Name", BigDecimal.TEN, "email@test.com", "1234567890",
                "EMAIL");
        when(springDataClientRepository.findById(id)).thenReturn(Optional.of(document));

        // Act
        Optional<Client> result = mongoClientRepositoryAdapter.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().id());
        assertEquals("Client Name", result.get().name());
        assertEquals(BigDecimal.TEN, result.get().balance());
        assertEquals("email@test.com", result.get().email());
        assertEquals("1234567890", result.get().phone());
        assertEquals(NotificationPreference.EMAIL, result.get().notificationPreference());
    }

    @Test
    void shouldSave() {
        // Arrange
        Client client = new Client("1", "Client Name", BigDecimal.TEN, "email@test.com", "1234567890",
                NotificationPreference.EMAIL);

        // Act
        mongoClientRepositoryAdapter.save(client);

        // Assert
        ArgumentCaptor<ClientDocument> captor = ArgumentCaptor.forClass(ClientDocument.class);
        verify(springDataClientRepository).save(captor.capture());
        ClientDocument savedDocument = captor.getValue();
        assertEquals("1", savedDocument.getId());
        assertEquals("Client Name", savedDocument.getName());
        assertEquals(BigDecimal.TEN, savedDocument.getBalance());
        assertEquals("email@test.com", savedDocument.getEmail());
        assertEquals("1234567890", savedDocument.getPhone());
        assertEquals("EMAIL", savedDocument.getNotificationPreference());
    }
}
