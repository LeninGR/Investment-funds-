package com.investment.funds.domain.model;

import java.math.BigDecimal;

public record Fund(String id, String name, BigDecimal minAmount, String category) {
}
