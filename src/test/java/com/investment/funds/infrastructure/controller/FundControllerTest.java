package com.investment.funds.infrastructure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.funds.application.usecase.UseCase;
import com.investment.funds.application.usecase.dto.CancelSubscribeInput;
import com.investment.funds.application.usecase.dto.SubscribeInput;
import com.investment.funds.domain.exception.BusinessException;
import com.investment.funds.infrastructure.controller.contract.CancelSubscriptionRequest;
import com.investment.funds.infrastructure.controller.contract.SubscribeRequest;

@WebMvcTest(FundController.class)
class FundControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private UseCase<SubscribeInput, Void> subscribeUseCase;

        @MockBean
        private UseCase<CancelSubscribeInput, Void> cancelSubscribeUseCase;

        @Test
        void subscribe_ShouldReturnOk_WhenSuccessful() throws Exception {
                // Arrange
                SubscribeRequest request = new SubscribeRequest();
                request.setClientId("client1");
                request.setFundId("fund1");

                // Act & Assert
                mockMvc.perform(post("/funds/subscribe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.message").value("Subscribed successfully"));

                verify(subscribeUseCase, times(1)).execute(new SubscribeInput("client1", "fund1"));
        }

        @Test
        void subscribe_ShouldReturnBadRequest_WhenBusinessException() throws Exception {
                // Arrange
                SubscribeRequest request = new SubscribeRequest();
                request.setClientId("client1");
                request.setFundId("fund1");

                doThrow(new BusinessException("Insufficient balance")).when(subscribeUseCase)
                                .execute(any(SubscribeInput.class));

                // Act & Assert
                mockMvc.perform(post("/funds/subscribe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.error").value("Insufficient balance"));
        }

        @Test
        void cancelSubscription_ShouldReturnOk_WhenSuccessful() throws Exception {
                // Arrange
                CancelSubscriptionRequest request = new CancelSubscriptionRequest();
                request.setClientId("client1");
                request.setFundId("fund1");

                // Act & Assert
                mockMvc.perform(post("/funds/cancel")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.message").value("Subscription cancelled successfully"));

                verify(cancelSubscribeUseCase, times(1)).execute(new CancelSubscribeInput("client1", "fund1"));
        }

        @Test
        void cancelSubscription_ShouldReturnBadRequest_WhenBusinessException() throws Exception {
                // Arrange
                CancelSubscriptionRequest request = new CancelSubscriptionRequest();
                request.setClientId("client1");
                request.setFundId("fund1");

                doThrow(new BusinessException("No active subscription")).when(cancelSubscribeUseCase)
                                .execute(any(CancelSubscribeInput.class));

                // Act & Assert
                mockMvc.perform(post("/funds/cancel")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.error").value("No active subscription"));
        }
}
