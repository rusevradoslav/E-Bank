package com.app.ebank.services.impl;

import com.app.ebank.domain.bindingModels.BankAccountBindingModel;
import com.app.ebank.domain.entities.BankAccount;
import com.app.ebank.domain.entities.Transaction;
import com.app.ebank.domain.entities.User;
import com.app.ebank.repositories.BankAccountRepository;
import com.app.ebank.services.BankAccountService;
import com.app.ebank.services.UserService;
import com.app.ebank.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;
    private final ValidationUtil validationUtil;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserService userService, ValidationUtil validationUtil) {
        this.bankAccountRepository = bankAccountRepository;
        this.userService = userService;
        this.validationUtil = validationUtil;
    }


    @Override
    public Set<BankAccount> findAllByOwnerUsername(Principal principal) {
        Set<BankAccount> bankAccounts = this.bankAccountRepository.findAllByOwnerUsername(principal.getName());
        return bankAccounts;
    }

    @Override
    public boolean createAccount(BankAccountBindingModel bankAccountBindingModel) {
        if (validationUtil.isValid(bankAccountBindingModel)) {
            BankAccount bankAccount = this.bankAccountRepository.findByIban(bankAccountBindingModel.getIban());


            if (bankAccount != null) {
                return false;
            }

            User user = this.userService.findUserByName(bankAccountBindingModel.getUsername());

            if (user == null) {
                return false;
            }
            bankAccount = new BankAccount();
            bankAccount.setOwner(user);
            bankAccount.setIban(bankAccountBindingModel.getIban());
            bankAccount.setBalance(BigDecimal.ZERO);

            this.bankAccountRepository.saveAndFlush(bankAccount);

            user.getBankAccounts().add(this.bankAccountRepository.findFirstById(bankAccount.getId()));
            return true;

        }
        return false;
    }

    @Override
    public void saveBankAccount(BankAccount bankAccount) {
        this.bankAccountRepository.save(bankAccount);
    }

    @Override
    public Set<BankAccount> getAllBankAccountsForTransfer(long id) {

        return this.bankAccountRepository
                .findAll().stream()
                .filter(bankAccount -> !bankAccount.getId().equals(id))
                .collect(Collectors.toSet());
    }

    @Override
    public BankAccountBindingModel extractAccountForTransaction(long id) {
        BankAccount bankAccount = this.bankAccountRepository.findFirstById(id);
        if (bankAccount==null){
            throw  new  IllegalArgumentException("Invalid Bank Account");
        }

        BankAccountBindingModel bankAccountBindingModel = new BankAccountBindingModel();
        bankAccountBindingModel.setId(bankAccount.getId());
        bankAccountBindingModel.setUsername(bankAccount.getOwner().getUsername());
        bankAccountBindingModel.setIban(bankAccount.getIban());

        return bankAccountBindingModel;
    }


    @Override
    public BankAccount findById(long id) {
        return this.bankAccountRepository.findFirstById(id);
    }


}
