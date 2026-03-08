package com.investment.funds.infrastructure.adapter.mongodb.repository;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.enums.NotificationPreference;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.infrastructure.adapter.mongodb.document.ClientDocument;

@Component
@Profile("!local")
public class MongoClientRepositoryAdapter implements ClientRepository {

    private final SpringDataClientRepository springDataClientRepository;

    public MongoClientRepositoryAdapter(SpringDataClientRepository springDataClientRepository) {
        this.springDataClientRepository = springDataClientRepository;
    }

    @Override
    public Optional<Client> findById(String id) {
        return springDataClientRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void save(Client client) {
        springDataClientRepository.save(toDocument(client));
    }

    private Client toDomain(ClientDocument document) {
        return new Client(
                document.getId(),
                document.getName(),
                document.getBalance(),
                document.getEmail(),
                document.getPhone(),
                NotificationPreference.valueOf(document.getNotificationPreference()));
    }

    private ClientDocument toDocument(Client client) {
        return new ClientDocument(
                client.id(),
                client.name(),
                client.balance(),
                client.email(),
                client.phone(),
                client.notificationPreference().name());
    }
}
