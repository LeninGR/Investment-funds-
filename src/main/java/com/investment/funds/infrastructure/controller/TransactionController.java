package com.investment.funds.infrastructure.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.funds.application.usecase.UseCase;
import com.investment.funds.application.usecase.dto.GetTransactionHistoryInput;
import com.investment.funds.domain.model.Transaction;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final UseCase<GetTransactionHistoryInput, List<Transaction>> getTransactionHistory;

    public TransactionController(UseCase<GetTransactionHistoryInput, List<Transaction>> getTransactionHistory) {
        this.getTransactionHistory = getTransactionHistory;
    }

    @GetMapping("/history/{clientId}")
    public List<Transaction> getHistory(@PathVariable String clientId) {
        return getTransactionHistory.execute(new GetTransactionHistoryInput(clientId));
    }
}
