package com.investment.funds.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.investment.funds.domain.model.enums.TransactionType;

public record Transaction(String id, String clientId, String fundId, TransactionType type, BigDecimal amount,
        LocalDateTime timestamp) {

    public Transaction(String clientId, String fundId, TransactionType type, BigDecimal amount) {
        this(UUID.randomUUID().toString(), clientId, fundId, type, amount, LocalDateTime.now());
    }
}
