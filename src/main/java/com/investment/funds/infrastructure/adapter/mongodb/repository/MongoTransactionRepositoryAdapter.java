package com.investment.funds.infrastructure.adapter.mongodb.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.investment.funds.domain.model.Transaction;
import com.investment.funds.domain.model.enums.TransactionType;
import com.investment.funds.domain.port.TransactionRepository;
import com.investment.funds.infrastructure.adapter.mongodb.document.TransactionDocument;

@Component
public class MongoTransactionRepositoryAdapter implements TransactionRepository {

    private final SpringDataTransactionRepository springDataTransactionRepository;

    public MongoTransactionRepositoryAdapter(SpringDataTransactionRepository springDataTransactionRepository) {
        this.springDataTransactionRepository = springDataTransactionRepository;
    }

    @Override
    public void save(Transaction transaction) {
        springDataTransactionRepository.save(toDocument(transaction));
    }

    @Override
    public List<Transaction> findByClientId(String clientId) {
        return springDataTransactionRepository.findByClientId(clientId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Transaction toDomain(TransactionDocument document) {
        return new Transaction(
                document.getId(),
                document.getClientId(),
                document.getFundId(),
                TransactionType.valueOf(document.getType()),
                document.getAmount(),
                document.getTimestamp());
    }

    private TransactionDocument toDocument(Transaction transaction) {
        return new TransactionDocument(
                transaction.id(),
                transaction.clientId(),
                transaction.fundId(),
                transaction.type().name(),
                transaction.amount(),
                transaction.timestamp());
    }
}
