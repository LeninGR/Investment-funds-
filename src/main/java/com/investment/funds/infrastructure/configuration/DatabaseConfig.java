package com.investment.funds.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.investment.funds.infrastructure.adapter.mongodb.repository")
public class DatabaseConfig {
}
