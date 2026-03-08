package com.investment.funds.infrastructure.adapter.notification;

import java.util.List;

import org.springframework.stereotype.Component;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.port.Notification;

@Component
public class NotificationAdapter implements Notification {

    private final List<NotificationStrategy> strategies;

    public NotificationAdapter(List<NotificationStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public void sendNotification(Client client, Fund fund, String message) {
        strategies.stream()
                .filter(strategy -> strategy.supports(client.notificationPreference()))
                .findFirst()
                .ifPresentOrElse(
                        strategy -> strategy.send(client, fund, message),
                        () -> System.out.println(
                                "No notification strategy found for preference: " + client.notificationPreference()));
    }
}
