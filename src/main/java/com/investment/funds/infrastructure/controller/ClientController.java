package com.investment.funds.infrastructure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.funds.application.usecase.UseCase;
import com.investment.funds.domain.model.Client;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final UseCase<String, Client> getClient;

    public ClientController(UseCase<String, Client> getClient) {
        this.getClient = getClient;
    }

    @GetMapping("/{clientId}")
    public Client getClient(@PathVariable String clientId) {
        return getClient.execute(clientId);
    }
}
