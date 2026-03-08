package com.investment.funds.application.usecase;

public interface UseCase<I, O> {
    O execute(I input);
}
