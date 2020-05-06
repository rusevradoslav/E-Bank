package com.app.ebank.services;

        import com.app.ebank.domain.bindingModels.BankAccountBindingModel;
        import com.app.ebank.domain.entities.Transaction;

        import java.util.Set;

public interface TransactionService {
    boolean depositAmount(BankAccountBindingModel bankAccountBindingModel);

    boolean withdrawAmount(BankAccountBindingModel bankAccountBindingModel);

    boolean transferAmount(BankAccountBindingModel bankAccountBindingModel);

    Set<Transaction> getTransactionsByUsername(String name);


}
