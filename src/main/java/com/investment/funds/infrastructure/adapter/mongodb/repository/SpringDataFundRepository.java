package com.investment.funds.infrastructure.adapter.mongodb.repository;

import com.investment.funds.infrastructure.adapter.mongodb.document.FundDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataFundRepository extends MongoRepository<FundDocument, String> {
}
