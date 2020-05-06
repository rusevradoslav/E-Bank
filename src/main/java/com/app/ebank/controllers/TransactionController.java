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
@RequestMapping("/accounts")
public class TransactionController {
    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;

    @Autowired
    public TransactionController(TransactionService transactionService, BankAccountService bankAccountService) {
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/deposit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deposit(@PathVariable("id") Long id, Model model) {
        BankAccountBindingModel bankAccountBindingModel = this.bankAccountService.extractAccountForTransaction(id);
        model.addAttribute("bankAccountBindingModel", bankAccountBindingModel);
        model.addAttribute("view", "accounts/deposit");

        return "fragments/layout";
    }

    @PostMapping("/deposit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String depositConfirm(Model model, @ModelAttribute("bankAccountBindingModel") BankAccountBindingModel bankAccountBindingModel) {
        if (!this.transactionService.depositAmount(bankAccountBindingModel)) {
            model.addAttribute("bankAccountBindingModel", bankAccountBindingModel);
            model.addAttribute("view", "accounts/deposit");

            return "fragments/layout";
        }
        return "redirect:/home";
    }


    @GetMapping("/withdraw/{id}")
    @PreAuthorize("isAuthenticated()")
    public String withdraw(@PathVariable("id") Long id, Model model) {
        BankAccountBindingModel bankAccountBindingModel = this.bankAccountService.extractAccountForTransaction(id);
        model.addAttribute("bankAccountBindingModel", bankAccountBindingModel);
        model.addAttribute("view", "accounts/withdraw");

        return "fragments/layout";
    }

    @PostMapping("/withdraw/{id}")
    @PreAuthorize("isAuthenticated()")
    public String withdrawConfirm(Model model, @ModelAttribute("bankAccountBindingModel") BankAccountBindingModel bankAccountBindingModel) {
        if (!this.transactionService.withdrawAmount(bankAccountBindingModel)) {
            model.addAttribute("bankAccountBindingModel", bankAccountBindingModel);
            model.addAttribute("view", "accounts/withdraw");

            return "fragments/layout";
        }
        return "redirect:/home";
    }

    @GetMapping("/transfer/{id}")
    @PreAuthorize("isAuthenticated()")
    public String transfer(@PathVariable("id") Long id, Model model) {
        BankAccountBindingModel bankAccountBindingModel = this.bankAccountService.extractAccountForTransaction(id);
        Set<BankAccount> bankAccounts = this.bankAccountService.getAllBankAccountsForTransfer(id);
        model.addAttribute("bankAccountBindingModel", bankAccountBindingModel);
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("view", "accounts/transfer");

        return "fragments/layout";
    }

    @PostMapping("/transfer/{id}")
    @PreAuthorize("isAuthenticated()")
    public String transactionConfirm(Model model, @ModelAttribute("bankAccountBindingModel") BankAccountBindingModel bankAccountBindingModel) {
        if (!this.transactionService.transferAmount(bankAccountBindingModel)) {

            model.addAttribute("bankAccountBindingModel", bankAccountBindingModel);

            model.addAttribute("view", "accounts/transfer");
            return "fragments/layout";
        }
        return "redirect:/home";
    }

}



