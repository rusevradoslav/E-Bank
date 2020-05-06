package com.app.ebank.services.impl;

import com.app.ebank.domain.bindingModels.BankAccountBindingModel;
import com.app.ebank.domain.entities.BankAccount;
import com.app.ebank.domain.entities.Transaction;
import com.app.ebank.domain.entities.TransactionType;
import com.app.ebank.repositories.TransactionRepository;
import com.app.ebank.services.BankAccountService;
import com.app.ebank.services.TransactionService;
import com.app.ebank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final BankAccountService bankAccountService;

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(BankAccountService bankAccountService, TransactionRepository transactionRepository) {
        this.bankAccountService = bankAccountService;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public boolean depositAmount(BankAccountBindingModel bankAccountBindingModel) {
        BankAccount bankAccount = this.bankAccountService.findById(bankAccountBindingModel.getId());

        if (bankAccount == null) {
            return false;
        }
        if (bankAccountBindingModel.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        bankAccount.setBalance(bankAccount.getBalance().add(bankAccountBindingModel.getAmount()));
        bankAccountService.saveBankAccount(bankAccount);
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setFromAccount(bankAccount);
        transaction.setToAccount(bankAccount);
        transaction.setAmount(bankAccountBindingModel.getAmount());
        this.transactionRepository.saveAndFlush(transaction);

        return true;
    }

    @Override
    public boolean withdrawAmount(BankAccountBindingModel bankAccountBindingModel) {

        BankAccount bankAccount = this.bankAccountService.findById(bankAccountBindingModel.getId());
        if (bankAccount == null) {
            return false;
        }
        if (bankAccountBindingModel.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        BigDecimal newBalance = bankAccount.getBalance().subtract(bankAccountBindingModel.getAmount());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        bankAccount.setBalance(newBalance);
        bankAccountService.saveBankAccount(bankAccount);
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setFromAccount(bankAccount);
        transaction.setToAccount(bankAccount);
        transaction.setAmount(bankAccountBindingModel.getAmount());
        this.transactionRepository.saveAndFlush(transaction);
        return true;
    }

    @Override
    public boolean transferAmount(BankAccountBindingModel bankAccountBindingModel) {
        BankAccount fromAccount = this.bankAccountService.findById(bankAccountBindingModel.getId());
        BankAccount toAccount = this.bankAccountService.findById(bankAccountBindingModel.getReceiverId());

        if (fromAccount == null || toAccount == null) {
            return false;
        }
        if (bankAccountBindingModel.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        BigDecimal newBalanceFromAccount = fromAccount.getBalance().subtract(bankAccountBindingModel.getAmount());
        if (newBalanceFromAccount.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        fromAccount.setBalance(newBalanceFromAccount);

        BigDecimal newBalanceToAccount = toAccount.getBalance().add(bankAccountBindingModel.getAmount());
        toAccount.setBalance(newBalanceToAccount);

        this.bankAccountService.saveBankAccount(fromAccount);
        this.bankAccountService.saveBankAccount(toAccount);
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        transaction.setToAccount(toAccount);
        transaction.setFromAccount(fromAccount);
        transaction.setAmount(bankAccountBindingModel.getAmount());
        transactionRepository.saveAndFlush(transaction);
        return true;

    }

    @Override
    public Set<Transaction> getTransactionsByUsername(String name) {

        LinkedHashSet<Transaction> collect = this.transactionRepository
                .findAll()
                .stream()
                .filter(transaction ->
                        transaction.getToAccount().getOwner().getUsername().equals(name)
                                || transaction.getToAccount().getOwner().getUsername().equals(name)
                ).sorted(Comparator.comparing(Transaction::getType))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println();
        return collect;
    }
}
