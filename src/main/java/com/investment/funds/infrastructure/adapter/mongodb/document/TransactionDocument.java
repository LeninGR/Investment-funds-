package com.investment.funds.infrastructure.adapter.mongodb.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transactions")
public class TransactionDocument {
    @Id
    private String id;
    private String clientId;
    private String fundId;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public TransactionDocument() {}

    public TransactionDocument(String id, String clientId, String fundId, String type, BigDecimal amount, LocalDateTime timestamp) {
        this.id = id;
        this.clientId = clientId;
        this.fundId = fundId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getFundId() { return fundId; }
    public void setFundId(String fundId) { this.fundId = fundId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
