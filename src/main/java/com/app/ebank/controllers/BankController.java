package com.app.ebank.controllers;

import com.app.ebank.domain.bindingModels.BankAccountBindingModel;
import com.app.ebank.domain.entities.BankAccount;
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
public class BankController {

    private BankAccountService bankAccountService;
    private TransactionService transactionService;

    @Autowired
    public BankController(BankAccountService bankAccountService, TransactionService transactionService) {
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(@ModelAttribute("bankAccountBindingModel") BankAccountBindingModel bankAccountBindingModel, Model model, Principal principal) {
        bankAccountBindingModel.setUsername(principal.getName());
        model.addAttribute("bankAccountBindingModel", bankAccountBindingModel);
        model.addAttribute("view", "accounts/create-account");
        return "fragments/layout";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createConfirm(@ModelAttribute("bankAccountBindingModel") BankAccountBindingModel bankAccountBindingModel, Model model) {
        if (!this.bankAccountService.createAccount(bankAccountBindingModel)) {
            model.addAttribute("bankAccountBindingModel", bankAccountBindingModel);
            model.addAttribute("view", "accounts/create-account");
            return "fragments/layout";
        }
        return "redirect:/home";
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
    public String transactionConfirm(@PathVariable("id") Long id,Model model, @ModelAttribute("bankAccountBindingModel") BankAccountBindingModel bankAccountBindingModel) {
        if (!this.transactionService.transferAmount(bankAccountBindingModel)) {
            Set<BankAccount> bankAccounts = this.bankAccountService.getAllBankAccountsForTransfer(id);
            model.addAttribute("bankAccountBindingModel", bankAccountBindingModel);
            model.addAttribute("bankAccounts", bankAccounts);
            model.addAttribute("view", "accounts/transfer");
            return "fragments/layout";
        }
        return "redirect:/home";
    }
}
