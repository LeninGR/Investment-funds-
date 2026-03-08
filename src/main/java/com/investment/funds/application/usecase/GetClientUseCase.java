package com.investment.funds.application.usecase;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.port.ClientRepository;

public class GetClientUseCase implements UseCase<String, Client> {

    private final ClientRepository clientRepository;

    public GetClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client execute(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }
}
