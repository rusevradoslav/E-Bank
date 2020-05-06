package com.app.ebank.domain.entities;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;




@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {


    private String username;
    private String email;
    private String password;
    private Set<BankAccount> bankAccounts;
    private Set<Role> authorities;

    public User() {
        this.bankAccounts= new HashSet<>();
        this.authorities= new HashSet<>();
    }

    @Override
    @Column(nullable = false,unique = true,updatable = false)
    public String getUsername() {
        return username;
    }
    @Column(nullable = false,unique = true,updatable = true)
    public String getEmail() {
        return email;
    }

    @Override
    @Column(nullable = false,updatable = true)
    public String getPassword() {
        return password;
    }
    @OneToMany(mappedBy = "owner")
    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    @Override
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}

