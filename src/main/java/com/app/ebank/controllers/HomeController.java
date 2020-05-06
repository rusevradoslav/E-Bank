package com.app.ebank.controllers;

import com.app.ebank.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {
    private final BankAccountService bankAccountService;

    @Autowired
    public HomeController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("view","index");
        return "fragments/layout";
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public String home(Principal principal,Model model){
        model.addAttribute("username",principal.getName());
        model.addAttribute("accounts",this.bankAccountService.findAllByOwnerUsername(principal));
        model.addAttribute("view","home");

        return "fragments/layout";
    }
}
