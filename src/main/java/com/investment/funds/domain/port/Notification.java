package com.investment.funds.domain.port;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;

public interface Notification {
    void sendNotification(Client client, Fund fund, String message);
}
