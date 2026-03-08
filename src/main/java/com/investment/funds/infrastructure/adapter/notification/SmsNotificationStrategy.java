package com.investment.funds.infrastructure.adapter.notification;

import org.springframework.stereotype.Component;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.enums.NotificationPreference;

@Component
public class SmsNotificationStrategy implements NotificationStrategy {
    @Override
    public boolean supports(NotificationPreference preference) {
        return NotificationPreference.SMS == preference;
    }

    @Override
    public void send(Client client, Fund fund, String message) {
        System.out.println("Sending SMS to " + client.phone() + ": " + message);
    }
}
