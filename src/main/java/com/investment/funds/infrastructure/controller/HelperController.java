package com.investment.funds.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.funds.application.usecase.UseCase;

@RestController
@RequestMapping("/helper")
public class HelperController {

    private final UseCase<Void, Void> helperUseCase;

    public HelperController(UseCase<Void, Void> helperUseCase) {
        this.helperUseCase = helperUseCase;
    }

    @PostMapping("/seed")
    public ResponseEntity<String> seedData() {
        helperUseCase.execute(null);
        
        return ResponseEntity.ok("Data seeded successfully");
    }
}
