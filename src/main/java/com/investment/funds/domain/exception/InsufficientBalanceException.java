package com.investment.funds.domain.exception;

public class InsufficientBalanceException extends BusinessException {
    public InsufficientBalanceException(String fundName) {
        super("No tiene saldo disponible para vincularse al fondo " + fundName);
    }
}
