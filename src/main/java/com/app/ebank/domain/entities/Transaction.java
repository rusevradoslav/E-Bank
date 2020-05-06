package com.app.ebank.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    private TransactionType type;
    private BankAccount fromAccount;
    private BankAccount toAccount;
    private BigDecimal amount;

    public Transaction() {
    }
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    public TransactionType getType() {
        return type;
    }
    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "sender",updatable = false)
    public BankAccount getFromAccount() {
        return fromAccount;
    }
    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "receiver",updatable = false)
    public BankAccount getToAccount() {
        return toAccount;
    }
    @Column(nullable = false)
    @DecimalMin(value = "0.00", message = "Amount must be a positive number")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setFromAccount(BankAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    public void setToAccount(BankAccount toAccount) {
        this.toAccount = toAccount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
