package com.investment.funds.infrastructure.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.investment.funds.application.usecase.UseCase;
import com.investment.funds.domain.model.Client;
import com.investment.funds.domain.model.enums.NotificationPreference;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UseCase<String, Client> getClient;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void getClient_ShouldReturnClientDetails_WhenExists() throws Exception {
        // Arrange
        String clientId = "1";
        Client client = new Client(clientId, "John Doe", new BigDecimal("500000"), "john@example.com", "1234567890",
                NotificationPreference.EMAIL);
        when(getClient.execute(clientId)).thenReturn(client);

        // Act & Assert
        mockMvc.perform(get("/clients/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.balance").value(500000))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}
