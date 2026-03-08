package com.investment.funds.infrastructure.adapter.mongodb.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.port.FundRepository;
import com.investment.funds.infrastructure.adapter.mongodb.document.FundDocument;

@Component
public class MongoFundRepositoryAdapter implements FundRepository {

    private final SpringDataFundRepository springDataFundRepository;

    public MongoFundRepositoryAdapter(SpringDataFundRepository springDataFundRepository) {
        this.springDataFundRepository = springDataFundRepository;
    }

    @Override
    public Optional<Fund> findById(String id) {
        return springDataFundRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void save(Fund fund) {
        springDataFundRepository.save(toDocument(fund));
    }

    private Fund toDomain(FundDocument document) {
        return new Fund(document.getId(), document.getName(), document.getMinAmount(), document.getCategory());
    }

    private FundDocument toDocument(Fund fund) {
        return new FundDocument(fund.id(), fund.name(), fund.minAmount(), fund.category());
    }
}
