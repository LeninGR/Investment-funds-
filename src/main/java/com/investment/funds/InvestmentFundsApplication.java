package com.investment.funds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.investment.funds.infrastructure.adapter.mongodb.repository")
public class InvestmentFundsApplication extends SpringBootServletInitializer {

	@Override
	protected org.springframework.boot.builder.SpringApplicationBuilder configure(
			org.springframework.boot.builder.SpringApplicationBuilder application) {
		return application.sources(InvestmentFundsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(InvestmentFundsApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner logConfig(org.springframework.context.ApplicationContext ctx) {
		return args -> {
			System.out.println(">>> DEBUG: Active Profiles: "
					+ java.util.Arrays.toString(ctx.getEnvironment().getActiveProfiles()));
			System.out.println(">>> DEBUG: Logging Level DispatcherServlet: " + ctx.getEnvironment()
					.getProperty("logging.level.org.springframework.web.servlet.DispatcherServlet"));
		};
	}
}
