package com.investment.funds.application.usecase;

import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.Fund;
import com.investment.funds.domain.port.ClientRepository;
import com.investment.funds.domain.port.FundRepository;

public class HelperUseCase {

    private final FundRepository fundRepository;
    private final ClientRepository clientRepository;

    public HelperUseCase(FundRepository fundRepository, ClientRepository clientRepository) {
        this.fundRepository = fundRepository;
        this.clientRepository = clientRepository;
    }

    public void createFund(Fund fund) {
        fundRepository.save(fund);
    }

    public void createClient(Client client) {
        clientRepository.save(client);
    }
}
