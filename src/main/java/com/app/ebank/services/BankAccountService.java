package com.app.ebank.services;

import com.app.ebank.domain.bindingModels.BankAccountBindingModel;
import com.app.ebank.domain.entities.BankAccount;
import com.app.ebank.domain.entities.Transaction;

import java.security.Principal;
import java.util.Set;

public interface BankAccountService {

    Set<BankAccount> findAllByOwnerUsername(Principal principal);

    boolean createAccount(BankAccountBindingModel bankAccountBindingModel);

    BankAccount findById(long id);



    void saveBankAccount(BankAccount bankAccount);

    Set<BankAccount> getAllBankAccountsForTransfer(long id);

    BankAccountBindingModel extractAccountForTransaction(long id);
}
