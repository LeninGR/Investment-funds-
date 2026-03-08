package com.investment.funds.application.usecase;

import java.util.List;

import com.investment.funds.application.usecase.dto.GetTransactionHistoryInput;
import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.service.TransactionService;

public class GetTransactionHistoryUseCase implements UseCase<GetTransactionHistoryInput, List<Transaction>> {

    private final TransactionService transactionService;

    public GetTransactionHistoryUseCase(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public List<Transaction> execute(GetTransactionHistoryInput input) {
        return transactionService.getHistory(input.clientId());
    }
}
