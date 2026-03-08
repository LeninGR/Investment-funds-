package com.investment.funds.domain.port;

import com.investment.funds.domain.model.Transaction;
import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findByClientId(String clientId);
}
