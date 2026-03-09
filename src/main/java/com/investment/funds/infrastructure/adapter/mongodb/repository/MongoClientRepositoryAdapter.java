package com.investment.funds.infrastructure.adapter.mongodb.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.enums.NotificationPreference;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.infrastructure.adapter.mongodb.document.ClientDocument;

@Component
@Profile("!local")
public class MongoClientRepositoryAdapter implements ClientRepository {

    private static final Logger logger = LoggerFactory.getLogger(MongoClientRepositoryAdapter.class);
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
        logger.info("Saving client with id: {}", client.id());
        ClientDocument document = toDocument(client);
        ClientDocument saved = springDataClientRepository.save(document);
        logger.info("Client saved successfully. ID: {}", saved.getId());
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
