package com.investment.funds.infrastructure.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.investment.funds.application.usecase.CancelSubscribeUseCase;
import com.investment.funds.application.usecase.GetClientUseCase;
import com.investment.funds.application.usecase.GetTransactionHistoryUseCase;
import com.investment.funds.application.usecase.HelperUseCase;
import com.investment.funds.application.usecase.SubscribeUseCase;
import com.investment.funds.application.usecase.UseCase;
import com.investment.funds.application.usecase.dto.CancelSubscribeInput;
import com.investment.funds.application.usecase.dto.GetTransactionHistoryInput;
import com.investment.funds.application.usecase.dto.SubscribeInput;
import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.domain.port.FundRepository;
import com.investment.funds.domain.port.Notification;
import com.investment.funds.domain.port.TransactionRepository;
import com.investment.funds.domain.service.TransactionService;

@Configuration
public class WireDependencies {

    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository) {
        return new TransactionService(transactionRepository);
    }

    // Use Cases
    @Bean
    public UseCase<String, Client> getClient(ClientRepository clientRepository) {
        return new GetClientUseCase(clientRepository);
    }

    @Bean
    public UseCase<SubscribeInput, Void> subscribe(
            FundRepository fundRepository,
            ClientRepository clientRepository,
            Notification notification,
            TransactionService transactionService) {
        return new SubscribeUseCase(fundRepository, clientRepository, notification, transactionService);
    }

    @Bean
    public UseCase<CancelSubscribeInput, Void> cancelSubscribe(
            FundRepository fundRepository,
            ClientRepository clientRepository,
            TransactionService transactionService) {
        return new CancelSubscribeUseCase(fundRepository, clientRepository, transactionService);
    }

    @Bean
    public UseCase<GetTransactionHistoryInput, List<Transaction>> getTransactionHistory(
            TransactionService transactionService) {
        return new GetTransactionHistoryUseCase(transactionService);
    }

    @Bean
    public UseCase<Void, Void> helper(ClientRepository clientRepository, FundRepository fundRepository) {
        return new HelperUseCase(clientRepository, fundRepository);
    }
}
