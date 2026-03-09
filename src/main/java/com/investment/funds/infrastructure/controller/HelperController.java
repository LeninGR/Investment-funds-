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
        System.out.println(">>> DEBUG: HelperController constructor called!");
        this.helperUseCase = helperUseCase;
    }

    @PostMapping("/seed")
    public ResponseEntity<String> seedData() {
        System.out.println(">>> DEBUG: HelperController.seedData() called!");
        helperUseCase.execute(null);
        System.out.println(">>> DEBUG: HelperController.seedData() executed helperUseCase!");
        return ResponseEntity.ok("Data seeded successfully");
    }
}
