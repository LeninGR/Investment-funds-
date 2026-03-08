package com.investment.funds.domain.port;

import java.util.Optional;

import com.investment.funds.domain.model.Client;

public interface ClientRepository {
    Optional<Client> findById(String id);

    void save(Client client);
}
