package com.app.ebank.domain.bindingModels;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserBindingModel {


    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public UserBindingModel() {
    }

    @NotNull
    @Length(min = 3, message = "Username must be at least 3 digits")
    public String getUsername() {
        return username;
    }

    /*@Pattern(regexp = "^[a-z0-9._]+@[a-z0-9.-]+[.][a-z.-]{2,6}$",message = "Invalid email")*/
    @Email(message = "Invalid Email")
    public String getEmail() {
        return email;
    }

    @NotNull
    @Length(min = 3, message = "Password must be at least 3 digits")
    public String getPassword() {
        return password;
    }

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,15}$", message = "Invalid password")
    public String getConfirmPassword() {
        return confirmPassword;
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

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
