package com.app.ebank.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;


@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    private Long id ;
    private User owner;
    private String iban;
    private BigDecimal balance;

    public BankAccount() {
    }

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    @ManyToOne
    public User getOwner() {
        return owner;
    }
    @Column(nullable = false,unique = true,updatable = false)
    public String getIban() {
        return iban;
    }
    @Column(nullable = false,updatable = true)
    @DecimalMin(value = "0.00",message = "Amount must be a positive number")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
