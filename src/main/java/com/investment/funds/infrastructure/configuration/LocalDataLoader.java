package com.investment.funds.infrastructure.configuration;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.enums.NotificationPreference;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.domain.port.FundRepository;

@Configuration
@Profile("local")
public class LocalDataLoader {

    @Bean
    public CommandLineRunner loadData(ClientRepository clientRepository, FundRepository fundRepository) {
        return args -> {
            // Load Clients
            if (clientRepository.findById("1").isEmpty()) {
                clientRepository.save(new Client("1", "John Doe", new BigDecimal("500000"), "john@example.com",
                        "1234567890", NotificationPreference.EMAIL));
                clientRepository.save(new Client("2", "Jane Smith", new BigDecimal("100000"), "jane@example.com",
                        "0987654321", NotificationPreference.SMS));
                System.out.println("Loaded local clients.");
            }

            // Load Funds
            if (fundRepository.findById("1").isEmpty()) {
                fundRepository.save(new Fund("1", "FPV_RECAUDADORA", new BigDecimal("75000"), "FPV"));
                fundRepository.save(new Fund("2", "FPV_ECOPETROL", new BigDecimal("125000"), "FPV"));
                fundRepository.save(new Fund("3", "DEUDAPRIVADA", new BigDecimal("50000"), "FIC"));
                fundRepository.save(new Fund("4", "FDO-ACCIONES", new BigDecimal("250000"), "FIC"));
                fundRepository.save(new Fund("5", "FPV_DINAMICO", new BigDecimal("100000"), "FPV"));
                System.out.println("Loaded local funds.");
            }
        };
    }
}
