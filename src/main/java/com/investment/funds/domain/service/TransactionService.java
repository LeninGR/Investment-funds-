package com.investment.funds.domain.service;

import java.util.List;

import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.model.enums.TransactionType;
import com.investment.funds.domain.port.TransactionRepository;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getHistory(String clientId) {
        return transactionRepository.findByClientId(clientId);
    }

    public boolean hasActiveSubscription(String clientId, String fundId) {
        List<Transaction> transactions = transactionRepository.findByClientId(clientId);

        return transactions.stream()
                .filter(t -> t.fundId().equals(fundId))
                .sorted((t1, t2) -> t2.timestamp().compareTo(t1.timestamp()))
                .findFirst()
                .map(t -> TransactionType.OPENING.equals(t.type()))
                .orElse(false);
    }
}
