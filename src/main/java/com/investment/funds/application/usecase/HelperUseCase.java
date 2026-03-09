package com.investment.funds.application.usecase;

import java.math.BigDecimal;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.enums.NotificationPreference;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.domain.port.FundRepository;

public class HelperUseCase implements UseCase<Void, Void> {

    private final ClientRepository clientRepository;
    private final FundRepository fundRepository;

    public HelperUseCase(ClientRepository clientRepository, FundRepository fundRepository) {
        this.clientRepository = clientRepository;
        this.fundRepository = fundRepository;
    }

    @Override
    public Void execute(Void input) {
        // Create Clients
        if (clientRepository.findById("1").isEmpty()) {
            clientRepository.save(new Client("1", "John Doe", new BigDecimal("500000"), "john@example.com",
                    "1234567890", NotificationPreference.EMAIL));
        }
        if (clientRepository.findById("2").isEmpty()) {
            clientRepository.save(new Client("2", "Jane Smith", new BigDecimal("100000"), "jane@example.com",
                    "0987654321", NotificationPreference.SMS));
        }

        // Create Funds
        if (fundRepository.findById("1").isEmpty()) {
            fundRepository.save(new Fund("1", "FPV_RECAUDADORA", new BigDecimal("75000"), "FPV"));
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

        return null;
    }
}
