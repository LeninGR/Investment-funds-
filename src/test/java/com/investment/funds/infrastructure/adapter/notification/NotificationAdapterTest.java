package com.investment.funds.infrastructure.adapter.notification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.enums.NotificationPreference;

@ExtendWith(MockitoExtension.class)
class NotificationAdapterTest {

    @Mock
    private NotificationStrategy emailStrategy;

    @Mock
    private NotificationStrategy smsStrategy;

    private NotificationAdapter notificationAdapter;

    @BeforeEach
    void setUp() {
        notificationAdapter = new NotificationAdapter(List.of(emailStrategy, smsStrategy));
    }

    @Test
    void sendNotification_ShouldUseSupportedStrategy() {
        // Arrange
        Client client = new Client("1", "Test Client", BigDecimal.TEN, "email@test.com", "1234567890",
                NotificationPreference.EMAIL);
        Fund fund = new Fund("1", "Test Fund", BigDecimal.TEN, "Category");
        String message = "Test Message";

        when(emailStrategy.supports(NotificationPreference.EMAIL)).thenReturn(true);
        // smsStrategy default mock behavior returns false for boolean, which is fine,
        // or we can be explicit if needed.

        // Act
        notificationAdapter.sendNotification(client, fund, message);

        // Assert
        verify(emailStrategy).send(client, fund, message);
        verify(smsStrategy, never()).send(any(), any(), any());
    }

    @Test
    void sendNotification_ShouldNotSend_WhenNoStrategySupported() {
        // Arrange
        Client client = new Client("1", "Test Client", BigDecimal.TEN, "email@test.com", "1234567890",
                NotificationPreference.SMS);
        Fund fund = new Fund("1", "Test Fund", BigDecimal.TEN, "Category");
        String message = "Test Message";

        when(emailStrategy.supports(NotificationPreference.SMS)).thenReturn(false);
        when(smsStrategy.supports(NotificationPreference.SMS)).thenReturn(false);

        // Act
        notificationAdapter.sendNotification(client, fund, message);

        // Assert
        verify(emailStrategy, never()).send(any(), any(), any());
        verify(smsStrategy, never()).send(any(), any(), any());
    }
}
