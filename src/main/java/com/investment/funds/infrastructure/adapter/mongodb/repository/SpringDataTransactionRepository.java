package com.investment.funds.infrastructure.adapter.mongodb.repository;

import com.investment.funds.infrastructure.adapter.mongodb.document.TransactionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpringDataTransactionRepository extends MongoRepository<TransactionDocument, String> {
    List<TransactionDocument> findByClientId(String clientId);
}
