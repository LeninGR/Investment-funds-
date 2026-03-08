package com.investment.funds.infrastructure.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.funds.application.usecase.HelperUseCase;
import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.model.enums.NotificationPreference;

@RestController
@RequestMapping("/funds/seed")
public class HelperController {

    private final HelperUseCase helperUseCase;

    public HelperController(HelperUseCase helperUseCase) {
        this.helperUseCase = helperUseCase;
    }

    @PostMapping("/funds")
    public ResponseEntity<?> seedFunds() {
        helperUseCase.createFund(new Fund("1", "FPV_RECAUDADORA", new BigDecimal("75000"), "FPV"));
        helperUseCase.createFund(new Fund("2", "FPV_ECOPETROL", new BigDecimal("125000"), "FPV"));
        helperUseCase.createFund(new Fund("3", "DEUDAPRIVADA", new BigDecimal("50000"), "FIC"));
        helperUseCase.createFund(new Fund("4", "FDO-ACCIONES", new BigDecimal("250000"), "FIC"));
        helperUseCase.createFund(new Fund("5", "FPV_DINAMICA", new BigDecimal("100000"), "FPV"));
        return ResponseEntity.ok(Map.of("message", "Funds seeded"));
    }

    @PostMapping("/clients")
    public ResponseEntity<?> seedClients() {
        helperUseCase.createClient(new Client("1", "Client One", new BigDecimal("500000"), "client.one@example.com",
                "3001234567", NotificationPreference.EMAIL));
        return ResponseEntity.ok(Map.of("message", "Default client seeded with $500.000 balance"));
    }
}
