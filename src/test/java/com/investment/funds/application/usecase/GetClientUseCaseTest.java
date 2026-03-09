package com.investment.funds.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.enums.NotificationPreference;
import com.investment.funds.domain.port.ClientRepository;

@ExtendWith(MockitoExtension.class)
class GetClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private GetClientUseCase getClientUseCase;

    @Test
    void shouldReturnClientWhenFound() {
        // Arrange
        String clientId = "1";
        Client client = new Client(clientId, "Test Client", new BigDecimal("500000"), "test@test.com", "1234567890",
                NotificationPreference.EMAIL);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        // Act
        Client result = getClientUseCase.execute(clientId);

        // Assert
        assertEquals(client, result);
    }

    @Test
    void execute_ShouldThrowException_WhenClientNotFound() {
        // Arrange
        String clientId = "1";
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> getClientUseCase.execute(clientId));
    }
}
