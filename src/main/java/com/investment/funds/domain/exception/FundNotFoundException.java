package com.investment.funds.domain.exception;

public class FundNotFoundException extends BusinessException {
    public FundNotFoundException(String id) {
        super("Fund not found with id: " + id);
    }
}
