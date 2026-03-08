package com.investment.funds.infrastructure.adapter.notification;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.enums.NotificationPreference;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy implements NotificationStrategy {
    @Override
    public boolean supports(NotificationPreference preference) {
        return NotificationPreference.EMAIL == preference;
    }

    @Override
    public void send(Client client, Fund fund, String message) {
        System.out.println("Sending EMAIL to " + client.email() + ": " + message);
    }
}
