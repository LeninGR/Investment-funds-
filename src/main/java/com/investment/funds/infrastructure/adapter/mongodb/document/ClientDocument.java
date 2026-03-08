package com.investment.funds.infrastructure.adapter.mongodb.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "clients")
public class ClientDocument {
    @Id
    private String id;
    private String name;
    private BigDecimal balance;
    private String email;
    private String phone;
    private String notificationPreference;

    public ClientDocument() {}

    public ClientDocument(String id, String name, BigDecimal balance, String email, String phone, String notificationPreference) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.email = email;
        this.phone = phone;
        this.notificationPreference = notificationPreference;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getNotificationPreference() { return notificationPreference; }
    public void setNotificationPreference(String notificationPreference) { this.notificationPreference = notificationPreference; }
}
