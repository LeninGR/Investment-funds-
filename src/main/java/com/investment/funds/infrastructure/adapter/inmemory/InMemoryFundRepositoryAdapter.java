package com.investment.funds.infrastructure.adapter.inmemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.port.FundRepository;

@Component
@Profile("local")
public class InMemoryFundRepositoryAdapter implements FundRepository {

    private final Map<String, Fund> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Fund> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(Fund fund) {
        store.put(fund.id(), fund);
    }
}
