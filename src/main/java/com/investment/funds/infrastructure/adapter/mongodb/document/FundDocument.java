package com.investment.funds.infrastructure.adapter.mongodb.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "funds")
public class FundDocument {
    @Id
    private String id;
    private String name;
    private BigDecimal minAmount;
    private String category;

    public FundDocument() {}

    public FundDocument(String id, String name, BigDecimal minAmount, String category) {
        this.id = id;
        this.name = name;
        this.minAmount = minAmount;
        this.category = category;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
