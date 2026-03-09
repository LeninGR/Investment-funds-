package com.investment.funds.infrastructure.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.investment.funds.application.usecase.UseCase;
import com.investment.funds.application.usecase.dto.CancelSubscribeInput;
import com.investment.funds.application.usecase.dto.SubscribeInput;
import com.investment.funds.domain.exception.BusinessException;
import com.investment.funds.infrastructure.controller.contract.CancelSubscriptionRequest;
import com.investment.funds.infrastructure.controller.contract.SubscribeRequest;

@RequestMapping("/funds")
@ResponseBody
public class FundController {

    private final UseCase<SubscribeInput, Void> subscribe;
    private final UseCase<CancelSubscribeInput, Void> cancelSubscribe;

    public FundController(UseCase<SubscribeInput, Void> subscribe,
            UseCase<CancelSubscribeInput, Void> cancelSubscribe) {
        this.subscribe = subscribe;
        this.cancelSubscribe = cancelSubscribe;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscribeRequest request) {
        try {
            subscribe.execute(new SubscribeInput(request.getClientId(), request.getFundId()));

            return ResponseEntity.ok(Map.of("message", "Subscribed successfully"));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelSubscription(@RequestBody CancelSubscriptionRequest request) {
        try {
            cancelSubscribe.execute(new CancelSubscribeInput(request.getClientId(), request.getFundId()));

            return ResponseEntity.ok(Map.of("message", "Subscription cancelled successfully"));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
