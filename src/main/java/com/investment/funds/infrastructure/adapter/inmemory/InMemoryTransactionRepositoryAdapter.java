package com.investment.funds.infrastructure.adapter.inmemory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.port.TransactionRepository;

@Component
@Profile("local")
public class InMemoryTransactionRepositoryAdapter implements TransactionRepository {

    private final Map<String, Transaction> store = new ConcurrentHashMap<>();

    @Override
    public void save(Transaction transaction) {
        store.put(transaction.id(), transaction);
    }

    @Override
    public List<Transaction> findByClientId(String clientId) {
        return store.values().stream()
                .filter(t -> t.clientId().equals(clientId))
                .collect(Collectors.toList());
    }
}
