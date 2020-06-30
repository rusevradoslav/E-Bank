package com.app.ebank.domain.bindingModels;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class BankAccountBindingModel {
    private Long id;
    private String username;
    private String iban;
    private BigDecimal amount;
    private Long receiverId;


    public BankAccountBindingModel() {
    }

    public Long getId() {
        return id;
    }

    @NotNull
    @NotEmpty
    public String getUsername() {
        return username;
    }

    @Size(min = 2, max = 10, message = " Iban must be 10 numbers")
    public String getIban() {
        return iban;
    }

    @DecimalMin(value = "0.00", message = "Amount must be a positive number")
    public BigDecimal getAmount() {
        return amount;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
