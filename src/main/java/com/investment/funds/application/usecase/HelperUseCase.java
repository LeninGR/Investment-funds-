package com.investment.funds.application.usecase;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.enums.NotificationPreference;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.domain.port.FundRepository;

public class HelperUseCase implements UseCase<Void, Void> {

    private static final Logger logger = LoggerFactory.getLogger(HelperUseCase.class);
    private final ClientRepository clientRepository;
    private final FundRepository fundRepository;

    public HelperUseCase(ClientRepository clientRepository, FundRepository fundRepository) {
        this.clientRepository = clientRepository;
        this.fundRepository = fundRepository;
    }

    @Override
    public Void execute(Void input) {
        logger.info("Starting HelperUseCase.execute()...");

        // Create Clients
        logger.info("Checking if Client 1 exists...");
        Optional<Client> client1 = clientRepository.findById("1");
        if (client1.isEmpty()) {
            logger.info("Client 1 not found. Creating it...");
            clientRepository.save(new Client("1", "John Doe", new BigDecimal("500000"), "john@example.com",
                    "1234567890", NotificationPreference.EMAIL));
            logger.info("Client 1 created.");
        } else {
            logger.info("Client 1 already exists: {}", client1.get());
        }

        logger.info("Checking if Client 2 exists...");
        if (clientRepository.findById("2").isEmpty()) {
            logger.info("Client 2 not found. Creating it...");
            clientRepository.save(new Client("2", "Jane Smith", new BigDecimal("100000"), "jane@example.com",
                    "0987654321", NotificationPreference.SMS));
            logger.info("Client 2 created.");
        }

        // Create Funds
        logger.info("Checking if Fund 1 exists...");
        Optional<Fund> fund1 = fundRepository.findById("1");
        if (fund1.isEmpty()) {
            logger.info("Fund 1 not found. Creating it...");
            fundRepository.save(new Fund("1", "FPV_RECAUDADORA", new BigDecimal("75000"), "FPV"));
            logger.info("Fund 1 created.");
        } else {
            logger.info("Fund 1 already exists: {}", fund1.get());
        }

        if (fundRepository.findById("2").isEmpty()) {
            fundRepository.save(new Fund("2", "FPV_ECOPETROL", new BigDecimal("125000"), "FPV"));
        }
        if (fundRepository.findById("3").isEmpty()) {
            fundRepository.save(new Fund("3", "DEUDAPRIVADA", new BigDecimal("50000"), "FIC"));
        }
        if (fundRepository.findById("4").isEmpty()) {
            fundRepository.save(new Fund("4", "FDO-ACCIONES", new BigDecimal("250000"), "FIC"));
        }
        if (fundRepository.findById("5").isEmpty()) {
            fundRepository.save(new Fund("5", "FPV_DINAMICO", new BigDecimal("100000"), "FPV"));
        }
        
        logger.info("HelperUseCase execution finished.");
        return null;
    }
}
