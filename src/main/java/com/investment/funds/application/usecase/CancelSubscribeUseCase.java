package com.investment.funds.application.usecase;

import com.investment.funds.application.usecase.dto.CancelSubscribeInput;
import com.investment.funds.domain.exception.BusinessException;
import com.investment.funds.domain.exception.FundNotFoundException;
import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.model.enums.TransactionType;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.domain.port.FundRepository;
import com.investment.funds.domain.service.TransactionService;

public class CancelSubscribeUseCase implements UseCase<CancelSubscribeInput, Void> {

    private final FundRepository fundRepository;
    private final ClientRepository clientRepository;
    private final TransactionService transactionService;

    public CancelSubscribeUseCase(FundRepository fundRepository,
            ClientRepository clientRepository,
            TransactionService transactionService) {
        this.fundRepository = fundRepository;
        this.clientRepository = clientRepository;
        this.transactionService = transactionService;
    }

    @Override
    public Void execute(CancelSubscribeInput input) {
        Client client = clientRepository.findById(input.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Fund fund = fundRepository.findById(input.fundId())
                .orElseThrow(() -> new FundNotFoundException(input.fundId()));

        if (!transactionService.hasActiveSubscription(input.clientId(), input.fundId())) {
            throw new BusinessException("No active subscription found for fund " + fund.name());
        }

        Client updatedClient = client.credit(fund.minAmount());

        Transaction transaction = new Transaction(input.clientId(), input.fundId(), TransactionType.CANCELLATION,
                fund.minAmount());

        clientRepository.save(updatedClient);
        transactionService.save(transaction);

        return null;
    }
}
