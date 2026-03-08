package com.investment.funds.domain.port;

import java.util.Optional;

import com.investment.funds.domain.model.Fund;

public interface FundRepository {
    Optional<Fund> findById(String id);

    void save(Fund fund);
}
