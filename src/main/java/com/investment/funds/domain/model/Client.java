package com.investment.funds.domain.model;

import java.math.BigDecimal;
import com.investment.funds.domain.model.enums.NotificationPreference;

public record Client(String id, String name, BigDecimal balance, String email, String phone,
        NotificationPreference notificationPreference) {

    public Client debit(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        return new Client(id, name, this.balance.subtract(amount), email, phone, notificationPreference);
    }

    public Client credit(BigDecimal amount) {
        return new Client(id, name, this.balance.add(amount), email, phone, notificationPreference);
    }
}
