package com.investment.funds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.investment.funds.infrastructure.adapter.mongodb.repository")
public class InvestmentFundsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestmentFundsApplication.class, args);
	}
}
