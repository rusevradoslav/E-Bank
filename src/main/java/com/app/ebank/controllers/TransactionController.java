package com.app.ebank.controllers;

import com.app.ebank.domain.bindingModels.BankAccountBindingModel;
import com.app.ebank.domain.entities.BankAccount;
import com.app.ebank.domain.entities.Transaction;
import com.app.ebank.services.BankAccountService;
import com.app.ebank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Controller
public class TransactionController {
    private final TransactionService transactionService;


    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping("/transactions")
    @PreAuthorize("isAuthenticated()")
    public String getAllTransactionsByUsername(Model model, Principal principal) {

        String username = principal.getName();
        Set<Transaction> transactions = this.transactionService.getTransactionsByUsername(username);
        model.addAttribute("view", "users/transactions");
        model.addAttribute("username", username);
        model.addAttribute("transactions",transactions);

        return "fragments/layout";
    }

}



