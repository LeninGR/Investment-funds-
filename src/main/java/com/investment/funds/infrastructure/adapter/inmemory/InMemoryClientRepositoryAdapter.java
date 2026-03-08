package com.investment.funds.infrastructure.adapter.inmemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.port.ClientRepository;

@Component
@Profile("local")
public class InMemoryClientRepositoryAdapter implements ClientRepository {

    private final Map<String, Client> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Client> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(Client client) {
        store.put(client.id(), client);
    }
}
