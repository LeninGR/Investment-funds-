package com.investment.funds.infrastructure.adapter.notification;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.enums.NotificationPreference;

public interface NotificationStrategy {
    boolean supports(NotificationPreference preference);
    void send(Client client, Fund fund, String message);
}
