package com.investment.funds.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

import com.investment.funds.application.usecase.dto.SubscribeInput;
import com.investment.funds.domain.exception.BusinessException;
import com.investment.funds.domain.exception.InsufficientBalanceException;
import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.model.enums.NotificationPreference;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.domain.port.FundRepository;
import com.investment.funds.domain.port.Notification;
import com.investment.funds.domain.service.TransactionService;

@ExtendWith(MockitoExtension.class)
class SubscribeUseCaseTest {

    @Mock
    private FundRepository fundRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private Notification notification;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private SubscribeUseCase subscribe;

    @Test
    void execute_ShouldSubscribeSuccessfully_WhenBalanceIsSufficient() {
        // Arrange
        String clientId = "client1";
        String fundId = "fund1";
        BigDecimal initialBalance = new BigDecimal("500000");
        BigDecimal minAmount = new BigDecimal("75000");

        Client client = new Client(clientId, "Client Test", initialBalance, "client@test.com", "1234567890",
                NotificationPreference.EMAIL);
        Fund fund = new Fund(fundId, "FPV_TEST_FUND", minAmount, "FPV");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(fundRepository.findById(fundId)).thenReturn(Optional.of(fund));
        when(transactionService.hasActiveSubscription(clientId, fundId)).thenReturn(false);

        // Act
        subscribe.execute(new SubscribeInput(clientId, fundId));

        // Assert
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientCaptor.capture());
        verify(transactionService).save(any(Transaction.class));
        verify(notification).sendNotification(any(Client.class), eq(fund), anyString());

        // Check that balance was deducted on the SAVED client
        assertEquals(new BigDecimal("425000"), clientCaptor.getValue().balance());
    }

    @Test
    void execute_ShouldThrowException_WhenAlreadySubscribed() {
        // Arrange
        String clientId = "client1";
        String fundId = "fund1";
        BigDecimal initialBalance = new BigDecimal("500000");
        BigDecimal minAmount = new BigDecimal("75000");

        Client client = new Client(clientId, "Client Test", initialBalance, "client@test.com", "1234567890",
                NotificationPreference.EMAIL);
        Fund fund = new Fund(fundId, "FPV_TEST_FUND", minAmount, "FPV");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(fundRepository.findById(fundId)).thenReturn(Optional.of(fund));
        when(transactionService.hasActiveSubscription(clientId, fundId)).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(BusinessException.class,
                () -> subscribe.execute(new SubscribeInput(clientId, fundId)));
        assertEquals("Client is already subscribed to fund " + fund.name(), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {
        // Arrange
        String clientId = "client1";
        String fundId = "fund1";
        BigDecimal initialBalance = new BigDecimal("50000");
        BigDecimal minAmount = new BigDecimal("75000");

        Client client = new Client(clientId, "Client Test", initialBalance, "client@test.com", "1234567890",
                NotificationPreference.EMAIL);
        Fund fund = new Fund(fundId, "FPV_TEST_FUND", minAmount, "FPV");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(fundRepository.findById(fundId)).thenReturn(Optional.of(fund));
        // Need to mock hasActiveSubscription for this test too, as it's called before
        // debit
        when(transactionService.hasActiveSubscription(clientId, fundId)).thenReturn(false);

        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> subscribe.execute(new SubscribeInput(clientId, fundId)));
    }
}
