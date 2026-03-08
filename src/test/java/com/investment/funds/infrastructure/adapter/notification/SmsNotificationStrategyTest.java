package com.investment.funds.infrastructure.adapter.notification;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.enums.NotificationPreference;

class SmsNotificationStrategyTest {

    private final SmsNotificationStrategy strategy = new SmsNotificationStrategy();

    @Test
    void shouldSupportSmsPreference() {
        assertTrue(strategy.supports(NotificationPreference.SMS));
    }

    @Test
    void shouldNotSupportEmailPreference() {
        assertFalse(strategy.supports(NotificationPreference.EMAIL));
    }

    @Test
    void shouldSendSmsNotification() {
        // Arrange
        Client client = new Client("1", "Test Client", BigDecimal.TEN, "email@test.com", "1234567890", NotificationPreference.SMS);
        Fund fund = new Fund("1", "Test Fund", BigDecimal.TEN, "Category");
        String message = "Test Message";

        // Act & Assert
        assertDoesNotThrow(() -> strategy.send(client, fund, message));
    }
}
