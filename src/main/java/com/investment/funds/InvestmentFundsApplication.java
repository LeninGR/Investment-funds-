package com.investment.funds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableMongoRepositories(basePackages = "com.investment.funds.infrastructure.adapter.mongodb.repository")
@Import(DispatcherServletAutoConfiguration.class)
public class InvestmentFundsApplication extends SpringBootServletInitializer {

	@Override
	protected org.springframework.boot.builder.SpringApplicationBuilder configure(
			org.springframework.boot.builder.SpringApplicationBuilder application) {
		return application.sources(InvestmentFundsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(InvestmentFundsApplication.class, args);
	}
}
