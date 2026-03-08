package com.investment.funds.application.usecase;

import com.investment.funds.application.usecase.dto.SubscribeInput;
import com.investment.funds.domain.exception.FundNotFoundException;
import com.investment.funds.domain.exception.InsufficientBalanceException;
import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.model.enums.TransactionType;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.domain.port.FundRepository;
import com.investment.funds.domain.port.Notification;
import com.investment.funds.domain.service.TransactionService;

public class SubscribeUseCase implements UseCase<SubscribeInput, Void> {

    private final FundRepository fundRepository;
    private final ClientRepository clientRepository;
    private final Notification notification;
    private final TransactionService transactionService;

    public SubscribeUseCase(FundRepository fundRepository,
            ClientRepository clientRepository,
            Notification notification,
            TransactionService transactionService) {
        this.fundRepository = fundRepository;
        this.clientRepository = clientRepository;
        this.notification = notification;
        this.transactionService = transactionService;
    }

    @Override
    public Void execute(SubscribeInput input) {
        Client client = clientRepository.findById(input.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Fund fund = fundRepository.findById(input.fundId())
                .orElseThrow(() -> new FundNotFoundException(input.fundId()));

        Client updatedClient;
        try {
            updatedClient = client.debit(fund.minAmount());
        } catch (IllegalArgumentException e) {
            throw new InsufficientBalanceException(fund.name());
        }

        Transaction transaction = new Transaction(input.clientId(), input.fundId(), TransactionType.OPENING,
                fund.minAmount());

        clientRepository.save(updatedClient);
        transactionService.save(transaction);
        notification.sendNotification(updatedClient, fund, "Subscribed to " + fund.name());

        return null;
    }
}
