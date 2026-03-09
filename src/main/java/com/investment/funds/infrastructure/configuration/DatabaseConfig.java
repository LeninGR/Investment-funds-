package com.investment.funds.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Profile("!local")
@EnableMongoRepositories(basePackages = "com.investment.funds.infrastructure.adapter.mongodb.repository")
public class DatabaseConfig {
}
