package com.app.ebank.controllers;

import com.app.ebank.domain.bindingModels.UserBindingModel;
import com.app.ebank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login(@ModelAttribute("userBindingModel") UserBindingModel userBindingModel, Model model) {
        System.out.println();
        model.addAttribute("userBindingModel",userBindingModel);
        model.addAttribute("view", "users/login-user");
        return "fragments/layout";
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(@ModelAttribute("userBindingModel") UserBindingModel userBindingModel, Model model) {
        model.addAttribute("userBindingModel", userBindingModel);
        model.addAttribute("view", "users/register-user");
        return "fragments/layout";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(@ModelAttribute("userBindingModel") UserBindingModel userBindingModel, Model model) {
        if (!userService.registerUser(userBindingModel)) {
            model.addAttribute("userBindingModel", userBindingModel);
            model.addAttribute("view", "users/register-user");
            return "fragments/layout";
        }
        return "redirect:/login";
    }
}
